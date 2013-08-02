package org.javaservicetemplate.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static final String CONFIG_FILE = "config.properties";
	private static final String CONNECTION_URL = "connection.url";
	private static final String CONNECTION_USERNAME = "connection.username";
	private static final String CONNECTION_PASSWORD = "connection.password";
	private static final String MANAGER_SLEEP_TIME = "manager.sleepTime";

	private static Configuration configuration;

	private Properties properties;

	public static synchronized Configuration getConfiguration() {
		if (configuration == null)
			configuration = new Configuration();

		return configuration;
	}

	private Configuration() {
		FileInputStream in = null;
		try {
			File directory = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
			File configFile = new File(directory, CONFIG_FILE);

			this.properties = new Properties();
			in = new FileInputStream(configFile);
			this.properties.load(in);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load configurations file", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				//just ignore
			}
		}
	}

	public String getConnectionURL() {
		return properties.getProperty(CONNECTION_URL);
	}

	public String getConnectionUser() {
		return properties.getProperty(CONNECTION_USERNAME);
	}

	public String getConnectionPassword() {
		return properties.getProperty(CONNECTION_PASSWORD);
	}

	public Long getManagerSleepTime() {
		return Long.valueOf(properties.getProperty(MANAGER_SLEEP_TIME));
	}

	public void setManagerSleepTime(Long sleepTime) {
		properties.setProperty(MANAGER_SLEEP_TIME, sleepTime.toString());
	}
}
