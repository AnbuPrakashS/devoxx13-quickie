package org.jboss.example.httpcaching.web.filtering;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Servlet Filter to include cache directives in the headers of the HTTP Response.
 */
@WebFilter(urlPatterns={"/*"})
public class LoggingFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
	
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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
		final HttpServletRequest servletRequest = (HttpServletRequest)request;
		final HttpServletResponse servletResponse = (HttpServletResponse)response;
		LOGGER.info("{} {} {}", servletResponse.getStatus(), servletRequest.getMethod(), servletRequest.getRequestURI());
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
