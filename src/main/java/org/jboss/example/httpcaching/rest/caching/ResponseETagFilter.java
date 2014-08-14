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
 * A {@link ContainerResponseFilter} that includes the {@code ETag} header in the successful responses, when the
 * associated {@link CacheService} has an Etag entry for the given Request URI.
 * 
 * @author Xavier Coulon
 * 
 */
@Provider
public class ResponseETagFilter implements ContainerResponseFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseETagFilter.class);

	public static final String ETAG = "ETag";

	@Inject
	private CacheService cacheService;

	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
			throws IOException {
		if (responseContext.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			final URI requestUri = requestContext.getUriInfo().getRequestUri();
			final String etag = cacheService.putETag(requestUri, responseContext.getEntity());
			if (etag != null) {
				LOGGER.debug("Adding ETag value in response for {} {} ", requestContext.getMethod(),
						requestUri.toASCIIString());
				responseContext.getHeaders().add(ETAG, etag);
			}
		}
	}

}
