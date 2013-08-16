package org.javaservicetemplate.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ShutdownSigner extends Thread {

	private static final Logger LOGGER = Logger.getLogger(ShutdownSigner.class);

	private List<Process> processes = new ArrayList<Process>();

	public void registerProcess(Process process) {
		processes.add(process);
	}

	@Override
	public void run() {
		LOGGER.info("Stop received");

		for (Process process : processes) {
			LOGGER.info(String.format("Stopping process %s", process.getClass().getSimpleName()));
			process.doStop();
			LOGGER.info("Process stopped!");
		}

		LOGGER.info("Service successfully stopped!");
	}
}
