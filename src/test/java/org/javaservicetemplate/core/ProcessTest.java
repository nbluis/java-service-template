package org.javaservicetemplate.core;

import static org.javaservicetemplate.core.Process.VERIFICATION_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.javaservicetemplate.util.TestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessTest {

	private Process process;
	private long sleepTime;
	private int processedTimes;
	private boolean throwError = false;
	private long ORIGINAL_VERIFICATION_TIME;

	@Before
	public void setUp() {
		sleepTime = 5000;
		processedTimes = 0;

		ORIGINAL_VERIFICATION_TIME = VERIFICATION_TIME;
		VERIFICATION_TIME = 1;

		process = new Process() {

			@Override
			long getSleepTime() {
				return sleepTime;
			}

			@Override
			void doProcess() {
				processedTimes++;
				if (throwError)
					throw new TestException();
			}
		};
	}

	@After
	public void tearDown() {
		VERIFICATION_TIME = ORIGINAL_VERIFICATION_TIME;
	}

	@Test
	public void shouldRespectTheProcessSleepTimeDuringProcess() {
		process.mustStop = true;
		process.run();
		process.run();
		process.run();

		assertEquals(processedTimes, 1);
	}

	@Test
	public void shouldCatchAnyErrorThatOccurrsDuringTheProcess() {
		throwError = true;
		process.mustStop = true;

		process.run();
	}

	@Test
	public void shouldSetToProcessStopWhenWaitIsCalled() {
		process.mustStop = true;
		process.run();
		process.doStop();

		assertTrue(process.stopped);
	}

}
