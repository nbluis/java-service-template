package org.javaservicetemplate.injection;

import org.hibernate.SessionFactory;
import org.javaservicetemplate.dao.AbstractDao;
import org.javaservicetemplate.interceptor.TransactionInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class InjectionModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SessionFactory.class).toProvider(SessionFactoryProvider.class);

		TransactionInterceptor interceptor = new TransactionInterceptor();
		requestInjection(interceptor);
		bindInterceptor(Matchers.subclassesOf(AbstractDao.class), Matchers.any(), interceptor);
	}

}
