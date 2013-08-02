package org.javaservicetemplate.core;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class ShutdownSignerTest {

	private ShutdownSigner signer;

	@Before
	public void setUp() {
		this.signer = new ShutdownSigner();
	}

	@Test
	public void shouldStopStatusBeFalseByDefault() {
		assertFalse(signer.mustStop());
	}

	@Test
	public void shouldShutdownProcessAfterReadyToStop() {
		signer.setReadyToStop();

		signer.run(); //sucess if the process is released
	}
}
