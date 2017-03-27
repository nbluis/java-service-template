package org.javaservicetemplate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.javaservicetemplate.model.Model;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public abstract class AbstractDaoMock<T extends Model<K>, K> {

	@Mock
	protected SessionFactory sessionFactory;
	@Mock
	protected Session session;
	@Mock
	protected Criteria criteria;
	@Mock
	protected Query query;
	@Mock
	protected SQLQuery sqlQuery;
	@Mock
	protected Filter filter;
	@Mock
	protected TypeResolver<T> typeResolver;

	@Captor
	protected ArgumentCaptor<Criterion> criterionCaptor;
	@Captor
	protected ArgumentCaptor<Order> orderCaptor;
	@Captor
	protected ArgumentCaptor<String> stringCaptor;
	@Captor
	protected ArgumentCaptor<Character> characterCaptor;
	@Captor
	protected ArgumentCaptor<Boolean> booleanCaptor;
	@Captor
	protected ArgumentCaptor<Long> longCaptor;
	@Captor
	protected ArgumentCaptor<Integer> integerCaptor;
	@Captor
	protected ArgumentCaptor<Projection> projectionCaptor;

	@Before
	public void before() {
		initMocks(this);
		setUpSessionFactory();
		setUpSession();
		setUpCriteria();
		setUpSQLQuery();
		setUpGenericType();
		setUp();
	}

	public void setUp() {
		//method do be overriden
	}

	private void setUpSessionFactory() {
		given(sessionFactory.getCurrentSession()).willReturn(session);
	}

	private void setUpSession() {
		given(session.createCriteria(any(Class.class))).willReturn(criteria);
		given(session.createQuery(anyString())).willReturn(query);
		given(session.createSQLQuery(anyString())).willReturn(sqlQuery);
		given(session.enableFilter(anyString())).willReturn(filter);
	}

	private void setUpCriteria() {
		given(criteria.add(any(Criterion.class))).willReturn(criteria);
		given(criteria.createAlias(anyString(), anyString())).willReturn(criteria);
	}

	private void setUpSQLQuery() {
		given(sqlQuery.addEntity(any(Class.class))).willReturn(sqlQuery);
	}

	private void setUpGenericType() {
		given(typeResolver.getTypeClass()).willReturn((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	public boolean isInitialized(Object proxy) {
		return Hibernate.isInitialized(proxy);
	}

	public void assertCriteria(String expectedCriteria) {
		verify(criteria, atLeast(1)).add(criterionCaptor.capture());
		assertContainsCriteria(criterionCaptor, expectedCriteria);
	}

	public void assertOrder(String expecrtedOrder) {
		verify(criteria, atLeast(1)).addOrder(orderCaptor.capture());
		assertEquals(expecrtedOrder, orderCaptor.getValue().toString());
	}

	public void assertNotContainsCriteria(ArgumentCaptor<Criterion> captor, String expectedCriteria) {
		assertNotContainsCriteria(captor.getAllValues(), expectedCriteria);
	}

	public void assertNotContainsCriteria(List<Criterion> criteriaList, String expectedCriteria) {
		if (containsCriteria(criteriaList, expectedCriteria))
			fail("The criterion list contains the expected criteria: " + expectedCriteria);
	}

	public void assertContainsCriteria(String expectedCriteria) {
		assertContainsCriteria(criterionCaptor, expectedCriteria);
	}

	public void assertContainsCriteria(ArgumentCaptor<Criterion> captor, String expectedCriteria) {
		assertContainsCriteria(captor.getAllValues(), expectedCriteria);
	}

	public void assertContainsCriteria(List<Criterion> criteriaList, String expectedCriteria) {
		if (!containsCriteria(criteriaList, expectedCriteria))
			fail("The criterion list cold not contains the expected criteria: " + expectedCriteria);
	}

	private boolean containsCriteria(List<Criterion> criteriaList, String criteria) {
		for (Criterion criterion : criteriaList) {
			if (criterion.toString().equals(criteria))
				return true;
		}
		return false;
	}

}
