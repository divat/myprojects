package com.cm.style.profile.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.style.profile.config.hibernate.Dao;
import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Styles;

/**
 * Styles DAO Implementation
 * @author DHIVAKART
 *
 */
@Repository
@Transactional
public class StylesDao extends Dao<Styles>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public StylesDao(){
		super(Styles.class);
	}
	
	/**
	 * Get the style details for publication
	 * @param publication
	 * @param client
	 * @param uID
	 * @return
	 */
	public Styles getStylesByPublication(Publication publication, Client client){
		Styles style = (Styles) getSession().createQuery("from Styles s inner join fetch s.publication p where s.isActive = :active and s.isDeleted = :deleted and s.isUpdatedStyle=:updatedStyle and p.id="+publication.getId()+" and s.client.id='" +client.getId()+"'")
				.setParameter("active", true)
				.setParameter("deleted", false)
				.setParameter("updatedStyle", true)
				.uniqueResult();
		return style;
	}
	
	/**
	 * Updated the existing styles
	 * @param publication
	 * @return
	 */
	public int updateStyle(Publication publication){	
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Query query = null;
		for(Styles s : publication.getStyle()){
			query = getSession().createQuery("update Styles set isUpdatedStyle = :isUpdatedStyle, modifiedOn=:modifiedOn where id=:styleId");
			query.setLong("styleId", s.getId());
			query.setBoolean("isUpdatedStyle", false);
			query.setTimestamp("modifiedOn", date);
		}
		int updateCount = query.executeUpdate();
		return updateCount;
	}
	
	/**
	 * Move the existing style to style history
	 * @param publication
	 * @return
	 */
	public int insertStyleHistory(Publication publication){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String HQL_STYLE_HISTORY_INSERT = "insert into styles_history(id, style_id, created_on,is_active,is_deleted,is_updated_style,modified_on,style_name,client_id,created_by,modified_by,publication_id,style_moved_on,is_style_profile_success) "
				+ "select nextval('styles_history_seq') as id,s.id as style_id,s.created_on,s.is_active,s.is_deleted,s.is_updated_style,s.modified_on,s.style_name,s.client_id,s.created_by,s.modified_by, "
				+ "psm.publication_id as publication_id, now() as style_moved_on, s.is_style_profile_success as is_style_profile_success "
				+ "from style_details s "
				+ "inner join publication_style_mapping psm on(psm.style_id=s.id) "
				+ "inner join publication_details pn on(pn.publication_id=psm.publication_id) "
				+ "where "
				+ "s.id=:styleId and s.is_updated_style=false and pn.publication_id="+publication.getId();
		
		Long styleId = 0L;
		for(Styles s : publication.getStyle()){
			styleId = s.getId();
		}
		
		Query query = getSession().createSQLQuery(HQL_STYLE_HISTORY_INSERT);
		query.setLong("styleId", styleId);
		int styleHistoryInsCount = query.executeUpdate();
		return styleHistoryInsCount;
	}
		
}