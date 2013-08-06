package org.javaservicetemplate.dao;

import static org.hibernate.criterion.Restrictions.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.javaservicetemplate.model.TestModel;
import org.junit.Test;
import org.mockito.InjectMocks;

public class AbstractDaoTest extends AbstractDaoMock<TestModel, Long> {

	@InjectMocks
	private TestDao dao;

	@Test
	public void shouldRetrieveASession() {
		given(sessionFactory.getCurrentSession()).willReturn(session);
		assertNotNull("Should retrieve the default sessionFactory session", dao.getSession());
	}

	@Test
	public void shouldCreateNewCriteriaBasedOnModelPersistentClass() {
		assertNotNull("Should criteria never be null", dao.getCriteria());
	}

	@Test
	public void shouldBuildCriteriaBasedOnCriterionsList() {
		assertNotNull(dao.getCriteria(Arrays.<Criterion> asList(eq("id", 1L))));

		verify(criteria).add(any(Criterion.class));
	}

	@Test
	public void shouldSaveSuccessfullyANewPersistentModel() {
		TestModel instance = new TestModel();
		assertNotNull("Should persist the model retrieving it", dao.save(instance));
		verify(session).saveOrUpdate(instance);
	}

	@Test
	public void shouldRetrieveNullWhenFindingAModelByEntityAndItNotExists() {
		Long unexistingId = 1L;
		given(criteria.list()).willReturn(Collections.emptyList());
		assertNull("Should retrieve null when not exists with the expected id", dao.findById(unexistingId));
	}

	@Test
	public void shouldFindByCriteria() {
		Long id = 1L;

		given(criteria.list()).willReturn(Arrays.asList(new TestModel()));
		List<TestModel> list = dao.findByCriteria(eq("id", id));

		assertEquals("Should retrieve the expected list size", 1, list.size());
		assertCriteria(String.format("id=%d", id));
	}

	@Test
	public void shouldFindByCriteriaUsingCriterionsList() {
		Long id = 1L;

		given(criteria.list()).willReturn(Arrays.asList(new TestModel()));
		List<TestModel> list = dao.findByCriteria(Arrays.<Criterion> asList(eq("id", id)));

		assertCriteria(String.format("id=%d", id));
		assertEquals("Should retrieve the expected list size", 1, list.size());
	}

	@Test
	public void shouldGetModelByCriteria() {
		TestModel expectedModel = new TestModel(1L);

		given(criteria.list()).willReturn(Arrays.asList(expectedModel));
		TestModel returnedModel = dao.getByCriteria(eq("id", expectedModel.getId()));

		assertCriteria(String.format("id=%d", expectedModel.getId()));
		assertEquals("Should retrieve the expected model", expectedModel.getId(), returnedModel.getId());
	}

	@Test
	public void shouldRetrieveNullWhenGetModelByCriteriaAndHasNoRowsWithExpectedRestrictions() {
		given(criteria.list()).willReturn(Collections.emptyList());

		TestModel returnedModel = dao.getByCriteria(eq("id", 1L));

		assertNull("Should retruned model be null", returnedModel);
	}

	@Test
	public void shouldDeleteAnInstance() {
		TestModel model = new TestModel();

		dao.delete(model);

		verify(session).delete(model);
	}

	@Test
	public void shouldReturnNullWhenGettingFirstByCriteriaThatReturnsEmpty() {
		given(criteria.list()).willReturn(Collections.emptyList());

		assertNull(dao.firstByCriteria(criteria));
	}

	@Test
	public void shouldReturnTheFirstReferenceWhenGettingFirstByCriteriaThatReturnsMoreThanOneElement() {
		TestModel expectedModel = new TestModel();
		TestModel anotherModel = new TestModel();

		given(criteria.list()).willReturn(Arrays.asList(expectedModel, anotherModel));

		assertEquals(expectedModel, dao.firstByCriteria(criteria));
	}

	@Test
	public void shouldInitializeLazyInstance() {
		TestModel model = new TestModel();

		dao.initialize(model);

		assertTrue(isInitialized(model));
	}

	@Test
	public void shouldCountObjectsByCriteria() {
		given(criteria.uniqueResult()).willReturn(2L);

		long resultCount = dao.countByCriteria(eq("active", Boolean.TRUE));

		assertEquals(2L, resultCount);
	}

	@Test
	public void shouldCountObjectsByCriterionList() {
		given(criteria.uniqueResult()).willReturn(2L);

		long resultCount = dao.countByCriteria(Arrays.<Criterion> asList(eq("active", Boolean.TRUE)));

		assertEquals(2L, resultCount);
	}

	@Test
	public void shouldFindAllReferencesByEntity() {
		List<TestModel> expectedList = Arrays.asList(new TestModel(), new TestModel());

		given(criteria.list()).willReturn(expectedList);

		assertEquals(expectedList, dao.findAll());
	}

	@Test
	public void shouldReturnTrueWhenVerifyingExistingObjectByCriteria() {
		given(criteria.uniqueResult()).willReturn(2L);

		assertTrue(dao.existsByCriteria(eq("active", true)));
	}

	@Test
	public void shouldReturnFalseWhenVerifyingNotExistingObjectByCriteria() {
		given(criteria.uniqueResult()).willReturn(0L);

		assertFalse(dao.existsByCriteria(eq("active", true)));
	}

	@Test
	public void shouldBePossibleToFindExistingByCriterionList() {
		given(criteria.uniqueResult()).willReturn(1L);

		assertTrue(dao.existsByCriteria(Arrays.<Criterion> asList(eq("active", true))));
	}

}
