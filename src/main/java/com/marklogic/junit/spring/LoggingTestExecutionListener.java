package com.marklogic.junit.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Simple test execution listener that logs the start and finish of each test method. This is often useful for finding
 * the occurrence of a particular test in a log file generated by a test suite run.
 */
public class LoggingTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggingTestExecutionListener.class);

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        logger.info("Starting test method: " + testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        logger.info("Finished test method: " + testContext.getTestMethod().getName());
    }

}
