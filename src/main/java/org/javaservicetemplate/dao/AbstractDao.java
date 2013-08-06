package org.javaservicetemplate.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.javaservicetemplate.model.Model;

import com.google.inject.Inject;

public abstract class AbstractDao<T extends Model<K>, K> {

	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private TypeResolver<T> typeResolver;

	protected Class<T> getModelClass() {
		return typeResolver.getTypeClass();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria getCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getModelClass());
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria;
	}

	protected Criteria getCriteria(List<Criterion> criterions) {
		return getCriteria(criterions.toArray(new Criterion[0]));
	}

	protected List<T> findByCriteria(Criterion... criterions) {
		return onRetrieved(getCriteria(criterions).list());
	}

	protected List<T> findByCriteria(List<Criterion> criterions) {
		return findByCriteria(criterions.toArray(new Criterion[0]));
	}

	protected T getByCriteria(Criterion... criterions) {
		List<T> list = findByCriteria(criterions);
		return (!list.isEmpty()) ? list.iterator().next() : null;
	}

	public long countByCriteria(Criterion... criterions) {
		Criteria criteria = getCriteria(criterions);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	public long countByCriteria(List<Criterion> criterions) {
		return countByCriteria(criterions.toArray(new Criterion[0]));
	}

	public boolean existsByCriteria(Criterion... criterions) {
		return countByCriteria(criterions) > 0;
	}

	public boolean existsByCriteria(List<Criterion> criterions) {
		return countByCriteria(criterions) > 0;
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	public T save(T instance) {
		getSession().saveOrUpdate(instance);
		return onRetrieved(instance);
	}

	public void delete(T instance) {
		getSession().delete(instance);
	}

	protected T firstByCriteria(Criteria criteria) {
		List<T> list = criteria.list();
		return ((!list.isEmpty()) ? onRetrieved(list.iterator().next()) : null);
	}

	private List<T> onRetrieved(List<T> list) {
		for (T instance : list) {
			onRetrieved(instance);
		}
		return list;
	}

	protected T onRetrieved(T instance) {
		return instance;
	}

	protected void initialize(Object proxy) {
		Hibernate.initialize(proxy);
	}

	public T findById(Long id) {
		return getByCriteria(eq("id", id));
	}

}
