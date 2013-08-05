package org.javaservicetemplate.injection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SessionFactoryProviderTest {

	@Test
	public void shouldSuccessfullCreateANewSessionFactory() {
		assertNotNull(new SessionFactoryProvider().get());
	}

}
