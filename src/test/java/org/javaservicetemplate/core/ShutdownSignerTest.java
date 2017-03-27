package org.javaservicetemplate.core;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ShutdownSignerTest {

	@InjectMocks
	private ShutdownSigner signer;
	@Mock
	private Process processOne;
	@Mock
	private Process processTwo;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void shouldSendStopToAllRegisteredProcessWhenReceiveAStop() {
		signer.registerProcess(processOne);
		signer.registerProcess(processTwo);
		signer.run();

		verify(processOne).doStop();
		verify(processTwo).doStop();
	}
}
