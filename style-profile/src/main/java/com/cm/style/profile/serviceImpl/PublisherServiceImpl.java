package com.cm.style.profile.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cm.style.profile.dao.PublicationDao;
import com.cm.style.profile.dao.PublisherDao;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.StyleDetails;
import com.cm.style.profile.request.wrapper.StyleWrapper;
import com.cm.style.profile.service.IPublisherService;

/**
 * Publisher service implementation
 * @author DHIVAKART
 *
 */
@Service
@Qualifier("publisherService")
public class PublisherServiceImpl implements IPublisherService{

	private static final Date utilDate = new Date();
	private static final Timestamp date = new Timestamp(utilDate.getTime());
	
	@Autowired
	PublisherDao publisherDao;
	
	@Autowired
	PublicationDao publicationDao;
	
	@Override
	public Publisher isPublisherExists(StyleWrapper request) {
		Publisher publisher = checkPublisherExists(request.getPublisherName());
		Publication publication = publicationDao.findByStringValue("publicationName", request.getPublicationName());
		if(publisher != null && publication != null){
			
		}else{
			
		}
		return null;
	}
	
	@Override
	public Publisher checkPublisherExists(String publisherName) {
		Publisher publisher = publisherDao.findByPublisher(publisherName);
		if(publisher != null){
			return publisher;
		}else{
			Publisher publisherObj = new Publisher();
			publisherObj.setPublisherName(publisherName);
			//publisherObj.setPublicationName(publicationName);
			publisherObj.setActive(true);
			publisherObj.setCreatedOn(date);
			//Users user =  
			//publisherObj.setCreatedBy();
			publisherObj.setDeleted(false);
			publisherObj = publisherDao.save(publisherObj);
			return publisherObj;
		}
		
	}

	@Override
	public Publisher save(Publisher publisher) {
		return publisherDao.save(publisher);
	}

	@Override
	public Publication findByPublicationName(String publicationName) {
		return null;
	}

	@Override
	public Publisher findByPublisherName(String publisherName) {
		return publisherDao.findByPublisher(publisherName);
	}

	@Override
	public List<StyleDetails> styleDetails(String createdBy) {		
		return publisherDao.getStyleDetails(createdBy);
	}

	@Override
	public List<Publisher> loadPublisher(String publisherName) {
		List<Publisher> publishers = publisherDao.findByString("publisherName", publisherName);
		return publishers;
	}

	
}