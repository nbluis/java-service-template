package org.javaservicetemplate.core;

public class ServiceManager extends Process {

	@Override
	void doProcess() {
		System.out.println("Running manager now!");
	}

	@Override
	long getSleepTime() {
		return 5000;
	}
}
