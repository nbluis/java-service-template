package org.javaservicetemplate.injection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.javaservicetemplate.core.Config;

import com.google.inject.Provider;

public class SessionFactoryProvider implements Provider<SessionFactory> {

	private static final SessionFactory factory = getSessionFactory();

	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.setProperty("hibernate.connection.url", Config.getConfiguration().getConnectionURL());
		configuration.setProperty("hibernate.connection.username", Config.getConfiguration().getConnectionUser());
		configuration.setProperty("hibernate.connection.password", Config.getConfiguration().getConnectionPassword());
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	public synchronized SessionFactory get() {
		return factory;
	}

}
