package org.javaservicetemplate.core;

import static java.lang.System.currentTimeMillis;
import static org.javaservicetemplate.core.Configuration.getConfiguration;
import static org.javaservicetemplate.injection.InjectorCreator.createInjector;

import org.apache.log4j.Logger;

import com.google.inject.Injector;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	private Injector injector;
	private ServiceManager manager;
	private ShutdownSigner shutDownSigner;

	protected void start() {
		this.injector = createInjector();
		setUpShutdownMonitor();
		startupManager();
		runMainLoop();
	}

	protected void setUpShutdownMonitor() {
		this.shutDownSigner = injector.getInstance(ShutdownSigner.class);
		Runtime.getRuntime().addShutdownHook(shutDownSigner);
	}

	protected void startupManager() {
		long initTime = currentTimeMillis();
		this.manager = injector.getInstance(ServiceManager.class);
		LOGGER.info(String.format("Service started in %d ms", currentTimeMillis() - initTime));
	}

	protected void runMainLoop() {
		long lastManagerRun = 0;

		try {
			do {
				if (shutDownSigner.mustStop()) {
					shutDownSigner.setReadyToStop();
					return;
				}

				long currentSleepTime = currentTimeMillis() - lastManagerRun;
				if (currentSleepTime >= getConfiguration().getManagerSleepTime()) {
					LOGGER.debug("Running manager now");
					lastManagerRun = currentTimeMillis();
					manager.run();
				}

				Thread.sleep(5000);
			} while (true);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		LOGGER.info("Bootstrapping Service");
		new Main().start();
	}

}
