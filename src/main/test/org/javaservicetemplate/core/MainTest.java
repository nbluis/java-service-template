package org.javaservicetemplate.core;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.javaservicetemplate.injection.InjectorCreator;
import org.javaservicetemplate.util.TestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.inject.Injector;

public class MainTest {

	private Main main;

	@Mock
	private Injector injector;
	@Mock
	private ShutdownSigner shutdownSigner;

	private ServiceManager manager = new ServiceManager() {

		private int calls = 0;

		public void run() {
			if (++calls == 2)
				throw new TestException();
		}
	};

	@Before
	public void setUp() {
		initMocks(this);
		InjectorCreator.mockInjector = injector;

		given(injector.getInstance(ShutdownSigner.class)).willReturn(shutdownSigner);
		given(injector.getInstance(ServiceManager.class)).willReturn(manager);
		this.main = new Main();
	}

	@After
	public void tearDown() {
		InjectorCreator.mockInjector = null; //should set null to other tests
	}

	@Test
	public void shouldCallImporterManagerContinuously() {
		Configuration.getConfiguration().setManagerSleepTime(1L);
		try {
			Main.main(null);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof TestException);
		}
	}

	@Test
	public void shouldMarkShutDownSignerToStopWhenTheProcessMustStop() {
		given(shutdownSigner.mustStop()).willReturn(true);

		main.start();

		verify(shutdownSigner).setReadyToStop();
	}

}
