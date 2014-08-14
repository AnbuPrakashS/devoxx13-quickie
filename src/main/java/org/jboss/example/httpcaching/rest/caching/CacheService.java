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
package org.jboss.example.httpcaching.rest.caching;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache service to verify if resources have expired/changed since the last time
 * the client requested them.
 * 
 * @author Xavier Coulon
 * 
 */
@Singleton
public class CacheService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CacheService.class);

	private final Map<String, String> etags = new HashMap<>();

	/**
	 * Returns the ETag associated with the request {@link URI}, or {@code null}
	 * if none matched
	 * 
	 * @param requestUri
	 *            the request {@link URI}, which should be obtained from the
	 *            {@link UriInfo} provided by the JAX-RS Implementation when
	 *            during client request
	 * @return the ETag value or {@code null}
	 * 
	 * @see UriInfo
	 */
	public String getETag(final URI requestUri) {
		try {
			final String url = requestUri.toURL().toExternalForm();
			final String etag = etags.get(url);
			LOGGER.debug("Retrieved ETag '{}' from URL '{}'", etag, url);
			return etag;
		} catch (MalformedURLException e) {
			LOGGER.error("Failed to retrieve ETag from given request URI {}",
					requestUri.toASCIIString(), e);
			return null;
		}
	}

	/**
	 * Computes and stores the ETag for the given Entity called on the request
	 * {@link URI}.
	 * 
	 * @param requestUri
	 *            the request {@link URI}, which should be obtained from the
	 *            {@link UriInfo} provided by the JAX-RS Implementation when
	 *            during client request
	 * @param entity
	 *            the entity that was returned to the user-agent
	 * @return the computed ETag value.
	 * 
	 * @see UriInfo
	 */
	public String putETag(final URI requestUri, final Object entity) {
		try {
			// basic ETag implementation based on timestamp
			final String etag = Long.toString(System.currentTimeMillis());
			final String url = requestUri.toURL().toExternalForm();
			LOGGER.debug("Storing ETag '{}' for URL '{}'", etag, url);
			etags.put(url, etag);
			return etag;
		} catch (MalformedURLException e) {
			LOGGER.error("Failed to store ETag from given request URI {}",
					requestUri.toASCIIString(), e);
			return null;
		}
	}

	/**
	 * Invalidates (ie: removes) all entries in the cache store for the given
	 * request {@link URI} as well as the entry for the parent {@link URI} and
	 * all its children {@link URI} (ie, the sibblings of the given {@link URI}
	 * ).
	 * 
	 * @param requestUri
	 *            the request {@link URI} for which the content has been
	 *            modified.
	 */
	public void invalidate(final URI requestUri) {
		// dumb approach for the example: clear all entries.
		LOGGER.debug("Clearing cached ETag entries");
		etags.clear();
	}

}
