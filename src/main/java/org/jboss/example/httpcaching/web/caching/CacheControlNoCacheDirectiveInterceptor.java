package org.jboss.example.httpcaching.web.caching;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * {@link ContainerResponseFilter} to include cache directives in the headers of the HTTP Response.
 */
@Provider
@CacheControlBinding 
public class CacheControlNoCacheDirectiveInterceptor implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().add("Cache-Control", "no-cache");
	}
	
}
