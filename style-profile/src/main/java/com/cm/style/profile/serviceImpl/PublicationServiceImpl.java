/**
 * 
 */
package com.cm.style.profile.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.cm.style.profile.dao.PublicationDao;
import com.cm.style.profile.dao.StylesDao;
import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.Styles;
import com.cm.style.profile.service.IPublicationService;

/**
 * Publication service implementation
 * @author DHIVAKART
 *
 */
@Service
@Qualifier("publicationService")
public class PublicationServiceImpl implements IPublicationService {
	private static final Logger logger = LoggerFactory.getLogger(PublicationServiceImpl.class);
	
	
	@Autowired
	PublicationDao publicationDao;

	@Autowired
	StylesDao stylesDao;
	
	@Override
	public Publication findByPublicationName(String pName, Publisher publisher) {
		Publication publication = publicationDao.findByPublication(pName, publisher);
		return publication;
	}

	/*@Override
	public Publication findByPublication(String publicationName, Publisher publisher, String styles, String uID, Client client) {
		logger.info("Capturing the publication details :: " +publicationName+"-"+publisher.getId());
		
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Publication publication = null;
		try {
			publication = findByPublicationName(publicationName, publisher);
			if(publication != null){
				Publication pStyle = publicationDao.findStylesByPublication(publication);
				if(pStyle != null && pStyle.getStyle().size() > 0){
					List<Styles> style = pStyle.getStyle();
					publication.setStyle(style);
				}
				return publication;
			}else{
				Publication publicationObj = new Publication();
				publicationObj.setPublicationName(publicationName);
				publicationObj.setPublisher(publisher);
				publicationObj.setActive(true);
				publicationObj.setDeleted(false);
				publicationObj.setCreatedOn(date);
				publicationDao.save(publicationObj);
				
				if(publicationObj != null){
					Styles style = persistStyles(styles, publicationObj, uID, client);
					if(style != null){
						logger.info("Styles captured for publication :: " +publicationObj.getId()+"-"+publicationObj.getPublicationName());
					}
				}
				
				//return publicationObj;
			}
		} catch (Exception e) {
			logger.error("Error while capturing the publication details :: " +publicationName+"-"+publisher.getId());
			e.printStackTrace();
		}
		return publication;
	}*/
	
	/**
	 * Add the styles
	 * @param styles
	 * @param publication
	 * @param uID
	 * @param client
	 * @return style
	 */
	private Styles persistStyles(String styles, Publication publication, Client client){
		logger.info("Persist styles for publication and client :: " +publication.getPublicationName()+"-"+client.getClientName());
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Styles style = null;
		try {
			style = new Styles();
			if(styles != null){
				style.setStyles(styles);
				style.setPublication(publication);
				style.setClient(client);
				//style.setuID(uID);
				style.setActive(true);
				style.setDeleted(false);
				style.setCreatedOn(date);
				style.setUpdatedStyle(true);
				stylesDao.save(style);
			}
		} catch (Exception e) {
			logger.error("Error while persisting the style details for publication :: " +publication.getPublicationName()+"-"+client.getClientName());
		}
		return style;
	}

	@Override
	public Publication findByPublication(String publicationName, Publisher publisher, String styles, Client client) {
		logger.info("Capturing the publication details :: " +publicationName+"-"+publisher.getId());
		
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Publication publication = null;
		try {
			publication = findByPublicationName(publicationName, publisher);
			if(publication != null){
				Publication pStyle = publicationDao.findStylesByPublication(publication);
				if(pStyle != null && pStyle.getStyle().size() > 0){
					List<Styles> style = pStyle.getStyle();
					publication.setStyle(style);
				}
				return publication;
			}else{
				Publication publicationObj = new Publication();
				publicationObj.setPublicationName(publicationName);
				publicationObj.setPublisher(publisher);
				publicationObj.setActive(true);
				publicationObj.setDeleted(false);
				publicationObj.setCreatedOn(date);
				publicationDao.save(publicationObj);
				
				if(publicationObj != null){
					Styles style = persistStyles(styles, publicationObj, client);
					if(style != null){
						logger.info("Styles captured for publication :: " +publicationObj.getId()+"-"+publicationObj.getPublicationName());
					}
				}
				
				//return publicationObj;
			}
		} catch (Exception e) {
			logger.error("Error while capturing the publication details :: " +publicationName+"-"+publisher.getId());
			e.printStackTrace();
		}
		return publication;
	}

	@Override
	public Publication addPublication(String publication, Publisher publisher) {
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		
		Publication publicationObj = new Publication();
		publicationObj.setPublicationName(publication);
		publicationObj.setPublisher(publisher);
		publicationObj.setActive(true);
		publicationObj.setDeleted(false);
		publicationObj.setCreatedOn(date);
		publicationDao.save(publicationObj);
		return publicationObj;
	}

	@Override
	public Publication findByPublicationName(String publicationName) {
		Publication publication = publicationDao.findByStringValue("publicationName", publicationName);
		return publication;
	}

	@Override
	public List<Publication> loadPublication(String publicationName) {
		List<Publication> publicationList = publicationDao.findByString("publicationName", publicationName);
		return publicationList;
	}

}