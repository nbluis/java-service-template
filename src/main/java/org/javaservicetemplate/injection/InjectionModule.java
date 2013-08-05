package org.javaservicetemplate.injection;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;

public class InjectionModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SessionFactory.class).toProvider(SessionFactoryProvider.class);
	}

}
