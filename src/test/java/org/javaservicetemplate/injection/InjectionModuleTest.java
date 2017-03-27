package org.javaservicetemplate.injection;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectionModuleTest {

	private Injector injector;

	@Before
	public void setUp() {
		this.injector = Guice.createInjector(new InjectionModule());
	}

	@Test
	public void shouldSuccessfullInjectSessionFactory() {
		assertNotNull(injector.getInstance(SessionFactory.class));
	}

}
