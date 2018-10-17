package com.cm.style.profile.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cm.style.profile.dao.PublicationDao;
import com.cm.style.profile.dao.StylesDao;
import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.Styles;
import com.cm.style.profile.service.IStyleService;

/**
 * Style service implementation
 * @author DHIVAKART
 *
 */
@Service
@Qualifier("styleService")
public class StyleServiceImpl implements IStyleService{

	@Autowired
	StylesDao styleDao;
	
	@Autowired
	PublicationDao publicationDao;

	@Override
	public Styles save(Styles style) {
		return styleDao.save(style);
	}

	/*@Override
	public Styles findStylesByPublication(String publicationName, Publisher publisher, Client client, String uID) {
		Publication publication = publicationDao.findByPublication(publicationName, publisher);
		Styles style = null;
		if(publication != null){
			style = styleDao.getStylesByPublication(publication, client, uID);
		}
		return style;
	}*/

	@Override
	public int update(Publication publication, String styles, Client clientName, Timestamp createdDate) {
		int count = styleDao.updateStyle(publication);
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		if(count > 0){
				Styles style = new Styles();
				if(styles != null){
					style.setStyles(styles);
					style.setPublication(publication);
					style.setClient(clientName);
					/*style.setuID(uID);*/
					style.setStyleProfileExecuted(false);
					style.setActive(true);
					style.setDeleted(false);
					style.setCreatedOn(createdDate);
					style.setModifiedOn(date);
					style.setUpdatedStyle(true);
					if(clientName.getClientName().equalsIgnoreCase("zinio")){
						style.setCreatedBy("zinio");
					}else{
						style.setCreatedBy("newsbank");
					}
					styleDao.save(style);
				}
			
			count = styleDao.insertStyleHistory(publication);
		}
		return count;
	}

	@Override
	public Styles findStylesByPublication(String publicationName, Publisher publisher, Client client) {
		Publication publication = publicationDao.findByPublication(publicationName, publisher);
		Styles style = null;
		if(publication != null){
			style = styleDao.getStylesByPublication(publication, client);
		}
		return style;
	}

	@Override
	public Styles updateStyleExecution(Styles style, String isStyleUpdated) {
		Styles styles = styleDao.findById(style.getId());
		if(styles != null){
			style.setStyleProfileExecuted(Boolean.valueOf(isStyleUpdated));
			styles = styleDao.update(style);
		}
		return styles;
	}

	@Override
	public Styles persistStyles(String style, Publication publication, Client client) {
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Styles styles = null;
		try {
			styles = new Styles();
			if(style != null){
				styles.setStyles(style);
				styles.setPublication(publication);
				styles.setClient(client);
				//style.setuID(uID);
				styles.setActive(true);
				styles.setDeleted(false);
				styles.setCreatedOn(date);
				styles.setUpdatedStyle(true);
				if(client.getClientName().equalsIgnoreCase("zinio")){
					styles.setCreatedBy("zinio");
				}else{
					styles.setCreatedBy("newsbank");
				}
				styleDao.save(styles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return styles;
	}
}