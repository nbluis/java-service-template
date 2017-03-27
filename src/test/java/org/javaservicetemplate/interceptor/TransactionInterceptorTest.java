package org.javaservicetemplate.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.javaservicetemplate.util.TestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionInterceptorTest {

	@InjectMocks
	private TransactionInterceptor interceptor;
	@Mock
	private SessionFactory sessionFactory;
	@Mock
	private Session session;
	@Mock
	private Transaction transaction;
	@Mock
	private MethodInvocation invocation;

	@Before
	public void setUp() {
		initMocks(this);
		given(sessionFactory.getCurrentSession()).willReturn(session);
		given(session.getTransaction()).willReturn(transaction);
	}

	@Test
	public void shouldStartTheTransactionWhenEnteringInANewMethod() throws Throwable {
		interceptor.invoke(invocation);

		verify(transaction).begin();
		verify(invocation).proceed();
	}

	@Test
	public void shouldCommitTheTransactionAfterProceedInvocation() throws Throwable {
		interceptor.invoke(invocation);

		verify(invocation).proceed();
		verify(transaction).commit();
	}

	@Test
	public void shouldReturnWhenInvocationValueAfterCommit() throws Throwable {
		Object returnValue = new Object();

		given(invocation.proceed()).willReturn(returnValue);

		assertEquals(returnValue, interceptor.invoke(invocation));
	}

	@Test
	public void shouldRollbackTheTransactionWhenExceptionOccursDuringProceed() throws Throwable {
		doThrow(new TestException()).when(invocation).proceed();

		try {
			interceptor.invoke(invocation);
			fail("Should throw fakeException");
		} catch (TestException e) {
			verify(transaction).rollback();
		}
	}

	@Test
	public void shouldNotStartAnotherTransactionWhenTheCurrentTransactionIsAlreadyIsActive() throws Throwable {
		given(transaction.isActive()).willReturn(true);

		interceptor.invoke(invocation);

		verify(transaction, never()).begin();
		verify(invocation).proceed();
	}
}
