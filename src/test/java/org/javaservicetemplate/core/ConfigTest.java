package org.javaservicetemplate.core;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	private Config configuration;

	@Before
	public void setUp() {
		this.configuration = Config.getConfiguration();
	}

	@Test
	public void shouldLoadConnectionURLFromConfigurationFile() {
		assertTrue(isNotEmpty(configuration.getConnectionURL()));
	}

	@Test
	public void shouldLoadConnectionUserFromConfigurationFile() {
		assertTrue(isNotEmpty(configuration.getConnectionUser()));
	}

	@Test
	public void shouldLoadConnectionPasswordFromConfigurationFile() {
		assertTrue(isNotEmpty(configuration.getConnectionPassword()));
	}

	@Test
	public void shouldLoadManagetSleepTimeFromConfigurationFile() {
		assertNotNull(configuration.getManagerSleepTime());
	}

}
