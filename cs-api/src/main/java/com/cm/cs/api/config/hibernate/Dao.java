package com.cm.cs.api.config.hibernate;

import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author DHIVAKART
 *
 * @param <E>
 */
public class Dao<E> {

	private final Class<E> entity;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Dao(Class<E> entity){
		this.entity = entity;
	}
	
	@Transactional
	public E save(E instance){
		sessionFactory.getCurrentSession().save(instance);
		return instance;
	}
	
	@Transactional
	public E update(E instance) {
		sessionFactory.getCurrentSession().saveOrUpdate(instance);
		return instance;
	}
	
	@Transactional
	public boolean persist(E transientInstance) {
		sessionFactory.getCurrentSession().persist(transientInstance);
		return true;
	}
	
	@Transactional
	public boolean remove(E transientInstance) {
		sessionFactory.getCurrentSession().delete(transientInstance);
		return true;
	}
	
	@Transactional
	public boolean merge(E detachedInstance) {
		sessionFactory.getCurrentSession().merge(detachedInstance);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public E findById(Long id) {
		E instance = (E) sessionFactory.getCurrentSession().get(entity, id);
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByStringField(String field, String value) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field+" = :data";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query).setString("data", value).list();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public E findByStringValue(String field, String value) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field+" = :data";
		E instance = (E) sessionFactory.getCurrentSession().createQuery(query).setString("data", value).uniqueResult();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByIntegerField(String field, Integer value) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field+" = :data";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query).setInteger("data", value).list();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findAll() {
		return (List<E>) sessionFactory.getCurrentSession().createCriteria(entity).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByString(String field, String value) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where lower("+field+") like :data";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query).setString("data", value + "%").list();
		return instance;
	}
}
