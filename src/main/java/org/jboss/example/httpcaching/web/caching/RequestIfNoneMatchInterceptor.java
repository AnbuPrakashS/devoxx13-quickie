/**
 * 
 */
package org.jboss.example.httpcaching.web.caching;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xcoulon
 *
 */
@Provider
@PreMatching
public class RequestIfNoneMatchInterceptor implements ContainerRequestFilter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RequestIfNoneMatchInterceptor.class);

	public static final String IF_NONE_MATCH = "If-None-Match";

	@Inject
	private CacheService cacheService;
	
	@Override
	public void filter(final ContainerRequestContext requestContext)
			throws IOException {
		final URI requestUri = requestContext.getUriInfo().getRequestUri();
		final String etag = cacheService.getETag(requestUri);
		requestContext.getHeaders();
		final String ifNoneMatchHeader = requestContext.getHeaderString(IF_NONE_MATCH);
		// nothing changed: abort with 304 NOT MODIFIED response
		if(ifNoneMatchHeader != null && ifNoneMatchHeader.equals(etag)) {
			LOGGER.debug("Aborting request since If-None-Match / ETag values match for {} {} ", requestContext.getMethod(), requestUri.toASCIIString());
			requestContext.abortWith(Response.status(Status.NOT_MODIFIED).build());
		}
	}

}
