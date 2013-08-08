package org.javaservicetemplate.interceptor;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TransactionInterceptor implements MethodInterceptor {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Transaction transaction = sessionFactory.getCurrentSession().getTransaction();

		if (transaction.isActive()) //nested calls into same transaction should not start another transaction
			return invocation.proceed();

		try {
			transaction.begin();
			Object returnValue = invocation.proceed();
			transaction.commit();
			return returnValue;
		} catch (Throwable e) {
			transaction.rollback();
			throw e;
		}
	}

}
