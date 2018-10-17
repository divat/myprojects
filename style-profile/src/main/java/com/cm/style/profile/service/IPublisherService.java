package com.cm.style.profile.service;

import java.util.List;

import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.StyleDetails;
import com.cm.style.profile.request.wrapper.StyleWrapper;

public interface IPublisherService {

	Publisher isPublisherExists(StyleWrapper wrapper);
	
	Publisher checkPublisherExists(String publisherName);
	
	Publication findByPublicationName(String publicationName);
	
	Publisher save(Publisher publisher);
	
	Publisher findByPublisherName(String publisherName);
	
	List<StyleDetails> styleDetails(String createdBy);
	
	List<Publisher> loadPublisher(String publisherName);
	
	/*List<StyleDetails> styleProfileData();*/
}
