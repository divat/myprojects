package com.codemantra.maestro.config.hibernate;

import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class Dao<E> {
	
	private final Class<E> entity;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
		 
	public Dao(Class<E> classe) {
		this.entity = classe;
	}

	@Transactional
	public E save(E instance) {
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
	
	/*@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByStringFields(String field1, String field2,String value) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field1+" = :data and "+field2+" = :data";
		//System.out.println("Query :: " +query+":"+value);
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query)
											.setString("data", value)
											.setString("data", val)
											.list();
		return instance;
	}*/
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByProjCategory(String field1, Integer value1, String field2, boolean value2) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field1+" = :data and "+field2+" = :isActive and stage_id=1 and leadId not in(24)";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query)
											.setInteger("data", value1)
											.setBoolean("isActive", value2)
											.list();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> findByProjCategory(String field1, Integer value1, String field2, boolean value2, String field3, Integer value3) {
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "from "+className+" where "+field1+" = :data and "+field2+" = :isActive and stage_id=1 and "+field3+" in (:value3)";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query)
											.setInteger("data", value1)
											.setBoolean("isActive", value2)
											.setParameter("value3", Long.valueOf(value3))
											.list();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> categoryList(Integer leadId){
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "select category from Leads l join l.categoryList category "
						+ "	where l.leadId = :leadId";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query)
											.setInteger("leadId", leadId)
											/*.setBoolean("is_active", true)*/
											.list();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<E> categoryList(Integer leadId, Integer categoryId){
		String expression = entity.toString();
		String className = new String();
		StringTokenizer st = new StringTokenizer(expression);
		while (st.hasMoreTokens()) {
			className = st.nextToken();
		}
		String query = "select category from Leads l join l.categoryList category "
						+ "	where l.leadId = :leadId and category.categoryId = :categoryId ";
		List<E> instance = sessionFactory.getCurrentSession().createQuery(query)
											.setInteger("leadId", leadId)
											.setInteger("categoryId", categoryId)
											/*.setBoolean("is_active", true)*/
											.list();
		return instance;
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
