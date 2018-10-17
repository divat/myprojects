package com.cm.style.profile.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.style.profile.config.hibernate.Dao;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.StyleDetails;

@Repository
@Transactional
public class PublisherDao extends Dao<Publisher>{

	private static final String FIND_PUBLISHER = "from Publisher where publisherName = :publisherName and isActive = :active and isDeleted = :deleted";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public PublisherDao(){
		super(Publisher.class);
	}
	
	/**
	 * Get the publisher details for 
	 * publisher name and publication
	 * @param publisherName
	 * @param publicationName
	 * @return
	 */
	public Publisher findByPublisher(String publisherName){
		Publisher publisher = (Publisher) getSession().createQuery(FIND_PUBLISHER)
				.setParameter("publisherName", publisherName)
				.setParameter("active", true)
				.setParameter("deleted", false)
				.uniqueResult();
		return publisher;
	}
	
	@SuppressWarnings("unchecked")
	public List<StyleDetails> getStyleDetails(String createdBy){
		String SQL = "select c.client_name,p.publisher_name,pn.publication_name,s.created_on,s.modified_on from publisher_details p "
				+ "inner join publisher_publication_mapping ppm on(ppm.publisher_id=p.publisher_id) "
				+ "inner join publication_details pn on(pn.publication_id=ppm.publication_id) "
				+ "inner join publication_style_mapping psm on(psm.publication_id=pn.publication_id) "
				+ "inner join style_details s on(s.id=psm.style_id) "
				+ "inner join client c on(c.client_id=s.client_id) "
				+ "where s.is_updated_style=true and c.client_name=:clientName and s.created_by=:createdBy";
		SQLQuery query = getSession().createSQLQuery(SQL);
		if(createdBy.equalsIgnoreCase("zinio")){
			query.setString("clientName", "Zinio");
		}else if(createdBy.equalsIgnoreCase("admin")){
			query.setString("clientName", "Zinio");
		}else{
			query.setString("clientName", "NewsBank");
		}
		
		if(createdBy.equalsIgnoreCase("admin")){
			query.setString("createdBy", "zinio");
		}else{
			query.setString("createdBy", createdBy);
		}
		
		
		List<Object[]> data = query.list();
		
		List<StyleDetails> styles = new ArrayList<StyleDetails>();
		for(Object[] row : data){
			StyleDetails style = new StyleDetails();
			style.setClientName(row[0].toString());
			style.setPublisherName(row[1].toString());
			style.setPublicatioName(row[2].toString());
			/*style.setStyleUID(row[3].toString());*/
			if(row[3] == null){
				style.setCreatedOn("-");
			}else{
				style.setCreatedOn(row[3].toString());
			}
			
			if(row[4] == null){
				style.setModifiedOn("-");
			}else{
				style.setModifiedOn(row[4].toString());
			}
			styles.add(style);
		}
		return styles;
	}
}
