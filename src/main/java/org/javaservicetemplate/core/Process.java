package org.javaservicetemplate.core;

import static java.lang.System.currentTimeMillis;

import org.slf4j.LoggerFactory;

public abstract class Process extends Thread {

	protected static long VERIFICATION_TIME = 3000;

	protected boolean mustStop = false;
	protected boolean stopped = false;
	private long lastProcessRun = 0;

	abstract void doProcess();

	abstract long getSleepTime();

	@Override
	public void run() {

		do {
			long currentSleepTime = currentTimeMillis() - lastProcessRun;

			if (currentSleepTime >= getSleepTime()) {
				lastProcessRun = currentTimeMillis();
				try {
					doProcess();
				} catch (Exception e) {
					LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
				}
			}

			try {
				Thread.sleep(VERIFICATION_TIME);
			} catch (InterruptedException e) {
				// ignore
			}
		} while (!mustStop);

		stopped = true;
	}

	public void doStop() {
		mustStop = true;
		while (!stopped) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
}
