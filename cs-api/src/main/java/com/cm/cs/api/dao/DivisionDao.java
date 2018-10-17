package com.cm.cs.api.dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.cs.api.config.hibernate.Dao;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.Division;

@Repository
@Transactional
public class DivisionDao extends Dao<Division>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public DivisionDao(){
		super(Division.class);
	}
	
	public Division findClientByDivision(Client client, Division division){
		Division divisionDetails = (Division) getSession().createQuery("from Division d inner join fetch d.client c where c.id="+client.getId()+" and d.id="+division.getId())
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.uniqueResult();
		return divisionDetails;
	}
}
