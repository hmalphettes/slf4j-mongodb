package org.slf4j.impl.mongo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration tests for {@link MongoLogger}.
 * 
 * @author <a href="mailto:christian.trutz@gmail.com">Christian Trutz</a>
 */
public class MongoLoggerIntegrationTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MongoLoggerIntegrationTest.class);

	@Test
	public void testTraceLogging() {
		LOGGER.trace("trace message");
		LOGGER.trace("trace message with npe", new NullPointerException(
				"test npe"));
	}

}
