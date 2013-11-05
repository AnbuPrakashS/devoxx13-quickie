/**
 * 
 */
package org.jboss.example.httpcaching.web.caching;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xcoulon
 * 
 */
@Provider
@CacheInvalidationBinding
public class CacheInvalidationInterceptor implements ContainerResponseFilter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CacheInvalidationInterceptor.class);

	public static final String ETAG = "ETag";

	@Inject
	private CacheService cacheService;

	@Override
	public void filter(final ContainerRequestContext requestContext,
			final ContainerResponseContext responseContext) throws IOException {
		if (responseContext.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			final URI requestUri = requestContext.getUriInfo().getRequestUri();
			LOGGER.info("Invalidating cache after successfull request execution on {} {} ",
					requestContext.getMethod() , requestUri.toASCIIString());
			cacheService.invalidate(requestUri);
		}
	}

}
