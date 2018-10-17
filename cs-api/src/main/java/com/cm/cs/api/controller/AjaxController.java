package com.cm.cs.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientList;
import com.cm.cs.api.service.IAutomationSerivce;
import com.cm.cs.api.service.IClientService;
import com.cm.cs.api.serviceImpl.ClientServiceImpl;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class AjaxController {

	private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
	
	@Autowired
	IAutomationSerivce automationService;
	
	@Autowired
	IClientService clientService;
	
	/**
	 * Get clients
	 * @param clientName
	 * @return
	 */
	/*@GetMapping(value = "/getAutomationTools")
	public ResponseEntity<List<AutomationTools>> getClientDetails(@RequestParam String automationName){
		log.info("Loading the client details for client {} " +automationName);
		try {
			List<AutomationTools> automationTools = automationService.loadAutomationTools(automationName);
			return new ResponseEntity<List<AutomationTools>>(automationTools, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	@GetMapping(value = "/getClients")
	public ResponseEntity<List<ClientList>> getClientDetails(@RequestParam String clientName){
		log.info("Loading the client details for client {} " +clientName);
		try {
			List<ClientList> clientList = clientService.loadClients(clientName);
			return new ResponseEntity<List<ClientList>>(clientList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
