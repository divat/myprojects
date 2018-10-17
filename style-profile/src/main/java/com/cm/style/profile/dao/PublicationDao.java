package com.cm.style.profile.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.style.profile.config.hibernate.Dao;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;

/**
 * Publication DAO Implementation
 * @author DHIVAKART
 *
 */
@Repository
@Transactional
public class PublicationDao extends Dao<Publication>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public PublicationDao(){
		super(Publication.class);
	}
	
	/**
	 * Get the publication details
	 * @param pName
	 * @param publisher
	 * @return
	 */
	public Publication findByPublication(String pName, Publisher publisher){
		String publicationName = "";
		if(pName.contains("'")){
			publicationName = pName.replaceAll("'", "''");
		}else{
			publicationName = pName;
		}
		Publication publicationObj = (Publication) getSession().createQuery("from Publication pn where pn.isActive=:active and pn.isDeleted=:deleted and pn.publicationName='"+publicationName+"' and pn.publisher.id="+publisher.getId())
				.setParameter("active", true)
				.setParameter("deleted", false)
				/*.setParameter("publicationName", publicationName)*/
				/*.setParameter("publisherId", Long.valueOf(publisher.getId()))*/
				.uniqueResult();
		/*if(publicationObj != null){
			publicationObj = findStylesByPublication(publicationObj);
		}*/
		
		return publicationObj;
	}
	
	/**
	 * Get the styles for publication
	 * @param publication
	 * @return
	 */
	public Publication findStylesByPublication(Publication publication){
		/*Publication p = (Publication) getSession().createQuery("from Publication pn left join pn.style s where pn.id="+publication.getId())*/
		Publication publicationObj = (Publication) getSession().createQuery("from Publication pn inner join fetch pn.style s where s.isUpdatedStyle=:isUpdatedStyle and pn.isActive=:active and pn.isDeleted=:deleted and pn.id="+publication.getId())
				.setParameter("active", true)
				.setParameter("deleted", false)
				.setParameter("isUpdatedStyle", true)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult();
		return publicationObj;
	}
}
