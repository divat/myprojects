package com.cm.style.profile.service;

import java.util.List;

import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;

/**
 * Publication service
 * @author DHIVAKART
 *
 */
public interface IPublicationService {

	/*Publication findByPublication(String publicationName, Publisher publisher, String styles, String uID, Client client);*/
	
	Publication findByPublication(String publicationName, Publisher publisher, String styles, Client client);
	
	Publication findByPublicationName(String publicationName, Publisher publisher);
	
	Publication addPublication(String publication, Publisher publisher);
	
	Publication findByPublicationName(String publicationName);
	
	List<Publication> loadPublication(String publicationName);
}
