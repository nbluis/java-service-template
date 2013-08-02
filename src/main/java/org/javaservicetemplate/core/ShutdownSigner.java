package org.javaservicetemplate.core;

import org.apache.log4j.Logger;

public class ShutdownSigner extends Thread {

	private static final Logger LOGGER = Logger.getLogger(ShutdownSigner.class);

	private boolean mustStop = false;
	private boolean readyToStop = false;

	public boolean mustStop() {
		return this.mustStop;
	}

	public void setReadyToStop() {
		this.readyToStop = true;
	}

	@Override
	public void run() {
		LOGGER.info("Stop received");
		mustStop = true;
		do {
			LOGGER.info("Waiting to stop");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {}
		} while (!readyToStop);
		LOGGER.info("Successfully stopped");
	}
}
