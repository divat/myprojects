package com.cm.style.profile.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.service.IClientService;
import com.cm.style.profile.service.IPublicationService;
import com.cm.style.profile.service.IPublisherService;
import com.cm.style.profile.serviceImpl.PublisherServiceImpl;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class AjaxController {

	private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
	
	@Autowired
	IClientService clientService;
	
	@Autowired
	IPublisherService publisherService;
	
	@Autowired
	IPublicationService publicationService;
	
	@GetMapping(value = "/getClientList")
	public ResponseEntity<List<Client>> getClientDetails(@RequestParam String clientName){
		log.info("Loading the client details for client {} " +clientName);
		try {
			List<Client> clientDetails = clientService.loadClients(clientName);
			return new ResponseEntity<List<Client>>(clientDetails, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = "/getPublisherList")
	public ResponseEntity<List<Publisher>> getPublisherDetails(@RequestParam String publisherName){
		log.info("Loading the publisher details for publisher {} " +publisherName);
		try {
			List<Publisher> publisherDetails = publisherService.loadPublisher(publisherName);
			return new ResponseEntity<List<Publisher>>(publisherDetails, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param publicationName
	 * @return
	 */
	@GetMapping(value = "/getPublicationList")
	public ResponseEntity<List<Publication>> getPublicationDetails(@RequestParam String publicationName){
		log.info("Loading the publication details for publication {} " +publicationName);
		try {
			List<Publication> publicationDetails = publicationService.loadPublication(publicationName);
			return new ResponseEntity<List<Publication>>(publicationDetails, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
