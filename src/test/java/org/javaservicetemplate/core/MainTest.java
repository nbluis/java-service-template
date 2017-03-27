package org.javaservicetemplate.core;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.javaservicetemplate.injection.InjectorCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.inject.Injector;

public class MainTest {

	@Mock
	private Injector injector;
	@Mock
	private ShutdownSigner shutdownSigner;
	@Mock
	private ServiceManager manager;

	@Before
	public void setUp() {
		initMocks(this);
		InjectorCreator.mockInjector = injector;

		given(injector.getInstance(ShutdownSigner.class)).willReturn(shutdownSigner);
		given(injector.getInstance(ServiceManager.class)).willReturn(manager);
	}

	@After
	public void tearDown() {
		InjectorCreator.mockInjector = null; //should set null to other tests
	}

	@Test
	public void shouldStartEachProcessWhenStarting() {
		Main.main(null);

		verify(manager).start();
	}
}
