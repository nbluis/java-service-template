package org.javaservicetemplate.core;

import static java.lang.System.currentTimeMillis;
import static org.javaservicetemplate.injection.InjectorCreator.createInjector;

import org.apache.log4j.Logger;

import com.google.inject.Injector;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	private Injector injector;
	private ServiceManager manager;
	private ShutdownSigner shutDownSigner;

	public void start() {
		long initTime = currentTimeMillis();

		injector = createInjector();
		setUpShutdownMonitor();
		setUpManager();
		startServices();

		LOGGER.info(String.format("Service started in %d ms", currentTimeMillis() - initTime));
	}

	private void setUpShutdownMonitor() {
		shutDownSigner = injector.getInstance(ShutdownSigner.class);
	}

	private void setUpManager() {
		this.manager = injector.getInstance(ServiceManager.class);
	}

	protected void startServices() {
		shutDownSigner.registerProcess(manager);

		Runtime.getRuntime().addShutdownHook(shutDownSigner);
		manager.start();
	}

	public static void main(String[] args) {
		LOGGER.info("Bootstrapping Service");
		new Main().start();
	}

}
