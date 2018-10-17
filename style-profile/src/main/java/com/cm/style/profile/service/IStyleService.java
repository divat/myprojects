package com.cm.style.profile.service;

import java.sql.Timestamp;

import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.Styles;

/**
 * Style service
 * @author DHIVAKART
 *
 */
public interface IStyleService {

	Styles save(Styles style);
	
	int update(Publication publication, String style, Client client, Timestamp createdDate);
	
	/*Styles findStylesByPublication(String publicationName, Publisher publisher, Client client, String uID);*/
	
	Styles findStylesByPublication(String publicationName, Publisher publisher, Client client);
	
	Styles updateStyleExecution(Styles style, String isStyleUpdated);
	
	Styles persistStyles(String style, Publication publication, Client client);
}
