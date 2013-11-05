package org.jboss.example.httpcaching;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPrinter extends TestWatcher {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TestPrinter.class);

	@Override
	public void starting(Description description) {
		LOGGER.info("**********************************************************************************");
		LOGGER.info("Starting test '{}'...", description.getMethodName());
		LOGGER.info("**********************************************************************************");
	}

	@Override
	public void finished(Description description) {
		LOGGER.info("**********************************************************************************");
		LOGGER.info("Finished test '{}'.", description.getMethodName());
		LOGGER.info("**********************************************************************************");
	}
}
