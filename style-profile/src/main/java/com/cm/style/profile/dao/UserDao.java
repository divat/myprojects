package com.cm.style.profile.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.style.profile.config.hibernate.Dao;
import com.cm.style.profile.model.Users;

/**
 * User DAO Implementaion
 * @author DHIVAKART
 *
 */
@Repository
public class UserDao extends Dao<Users>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public UserDao(){
		super(Users.class);
	}
	
	public Users findByUserName(String username){
		Users user = (Users) getSession().createQuery("from Users d where d.username = :username")
				.setParameter("username", username)
				.uniqueResult();
		return user;
	}
}
