/**
 * Copyright (c) 2014 Xavier Coulon and contributors (see git log)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.jboss.example.httpcaching;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.example.httpcaching.rest.caching.RequestIfNoneMatchInterceptor;
import org.jboss.example.httpcaching.util.TestPrinter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/**
 * @author Xavier Coulon
 * 
 */
@RunWith(Arquillian.class)
@RunAsClient
public class IntegrationTestCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestCase.class);

	@Rule
	public TestPrinter watchman = new TestPrinter();

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive webarchive = ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true, IntegrationTestCase.class.getPackage())
				.addAsLibraries(
						Maven.resolver().loadPomFromFile("pom.xml")
								.importDependencies(ScopeType.COMPILE).resolve().withTransitivity()
								.asFile()).addAsResource("logback-test.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		LOGGER.info("WebArchive content:\n{}", webarchive.toString(true));
		return webarchive;
	}

	@Test
	@RunAsClient
	public void shouldRetrieveAttendeesAgain(@ArquillianResource URL baseURL) {
		final String location = baseURL.toString() + "content/attendees";
		// 1st fetch
		expect().statusCode(200).body(containsString("firstName")).header("ETag", notNullValue()).when().get(location);
		// 2nd fetch, using some foo etag value
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, "foo").expect().statusCode(200)
				.body(containsString("firstName")).header("ETag", notNullValue()).when().get(location);
	}

	@Test
	@RunAsClient
	public void shouldNotRetrieveAttendeesAgain(@ArquillianResource URL baseURL) {
		final String location = baseURL.toString() + "content/attendees";
		// 1st fetch
		final Response response = expect().statusCode(200).body(containsString("firstName"))
				.header("ETag", notNullValue()).when().get(location);
		final String etag = response.getHeader("ETag");
		// 2nd fetch, using the etag retrieved before
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag).expect().statusCode(304).when().get(location);
		// 3rd fetch, using the etag retrieved before
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag).expect().statusCode(304).when().get(location);
	}

	@Test
	@RunAsClient
	public void shouldRetrieveAttendeesAgainAfterCreation(@ArquillianResource URL baseURL) {
		final String location = baseURL.toString() + "content/attendees/";
		// 1st fetch
		final Response response = expect().statusCode(200).body(containsString("firstName"))
				.header("ETag", notNullValue()).when().get(location);
		final String etag = response.getHeader("ETag");
		// attendee creation
		given().body("{\"firstName\":\"foo\"}").contentType(ContentType.JSON).expect().statusCode(201)
				.body("id", is(2)).header("Location", location + "2").when().post(location);
		// 2nd fetch, using the etag retrieved before
		final Response response2 = given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag).expect()
				.statusCode(200).when().get(location);
		// 3rd fetch, using the etag retrieved before
		final String etag2 = response2.getHeader("ETag");
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag2).expect().statusCode(304).when()
				.get(location);
	}

	@Test
	@RunAsClient
	public void shouldRetrieveAttendeesAgainAfterUpdate(@ArquillianResource URL baseURL) {
		final String location = baseURL.toString() + "content/attendees/";
		// 1st fetch
		final Response response = expect().statusCode(200).body(containsString("firstName"))
				.header("ETag", notNullValue()).when().get(location);
		final String etag = response.getHeader("ETag");
		// attendee creation
		given().body("{\"id\":0, \"firstName\":\"foo\"}").contentType(ContentType.JSON).expect().statusCode(200)
				.body("id", is(0)).when().put(location + "0");
		// 2nd fetch, using the etag retrieved before
		final Response response2 = given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag).expect()
				.statusCode(200).when().get(location);
		// 3rd fetch, using the etag retrieved before
		final String etag2 = response2.getHeader("ETag");
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag2).expect().statusCode(304).when()
				.get(location);
	}
	
	@Test
	@RunAsClient
	public void shouldRetrieveAttendeesAgainAfterDeletion(@ArquillianResource URL baseURL) {
		final String location = baseURL.toString() + "content/attendees/";
		// 1st fetch
		final Response response = expect().statusCode(200).body(containsString("firstName"))
				.header("ETag", notNullValue()).when().get(location);
		final String etag = response.getHeader("ETag");
		// attendee creation
		expect().statusCode(204).when().delete(location + "0");
		// 2nd fetch, using the etag retrieved before
		final Response response2 = given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag).expect()
				.statusCode(200).when().get(location);
		// 3rd fetch, using the etag retrieved before
		final String etag2 = response2.getHeader("ETag");
		given().header(RequestIfNoneMatchInterceptor.IF_NONE_MATCH, etag2).expect().statusCode(304).when()
				.get(location);
	}
}
