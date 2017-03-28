package org.javaservicetemplate.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownSigner extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownSigner.class);

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
