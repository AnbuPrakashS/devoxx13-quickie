package org.jboss.example.httpcaching.web.filtering;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

/**
 * Servlet Filter to log <strong>all</strong> incoming {@link ServletRequest}
 * method and request URI and {@link ServletResponse} status code.
 */
@WebFilter(urlPatterns={"/*"})
public class LoggingFilter implements Filter {

	@Inject
	private Logger logger;
	
	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
		final HttpServletRequest servletRequest = (HttpServletRequest)request;
		final HttpServletResponse servletResponse = (HttpServletResponse)response;
		logger.info("{} {} -> {}", servletRequest.getMethod(), servletRequest.getRequestURI(), servletResponse.getStatus());
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
