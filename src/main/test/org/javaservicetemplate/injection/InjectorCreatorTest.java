package org.javaservicetemplate.injection;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.google.inject.Injector;

public class InjectorCreatorTest {

	@Test
	public void shouldCreateInjectorUsingInjectionModule() {
		Injector injector = InjectorCreator.createInjector();
		assertNotNull(injector.getInstance(SessionFactory.class));
	}

}
