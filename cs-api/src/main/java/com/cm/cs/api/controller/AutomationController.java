package com.cm.cs.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cm.cs.api.model.AutomationMetrics;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.response.ApiResponse;
import com.cm.cs.api.service.IAutomationMetricsService;
import com.cm.cs.api.service.IAutomationSerivce;

/**
 * 
 * @author DHIVAKART
 *
 */
@RestController
public class AutomationController {

	private static final Logger logger = LoggerFactory.getLogger(AutomationController.class);
	
	@Autowired
	IAutomationSerivce automationService;
	
	@Autowired
	IAutomationMetricsService automationMetricsService;
	
	/**
	 * Handle the automation details
	 * @param projName
	 * @param clientName
	 * @param automationName
	 * @param developerName
	 * @param division
	 * @return
	 */
	@PostMapping(value = "/addToolMetrics")
	public ResponseEntity<ApiResponse> getAutomationDetails(@RequestParam(value="toolName") String automationName, @RequestParam(value="pageOrImageCount") String pageCount, 
			@RequestParam(value="jobId")String jobId, @RequestParam(value="jobType") String jobType){
		logger.info("Capturing the automation details {} " +automationName);
		
		ApiResponse response = new ApiResponse();
		AutomationTools automationDetails = null;
		AutomationMetrics metrics = null;
		try {
			if(automationName != null && pageCount != null && jobId != null && jobType != null){
				automationDetails = automationService.findByAutomationDetails(automationName, pageCount, jobId, jobType);
				if(automationDetails != null){
					metrics = automationMetricsService.saveAutomcationMetrics(automationDetails, jobId, pageCount, jobType);
					if(metrics != null){
						response.setAutomationName(automationDetails.getAutomationName());
						response.setMessage("Metrics captured successfully.");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setAutomationName(automationDetails.getAutomationName());
						response.setMessage("Metrics not captured successfully.");
						response.setStatus(HttpStatus.NOT_FOUND);
					}
				}else{
					response.setAutomationName(automationName);
					response.setMessage("Tool not exists.");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setAutomationName(automationName);
				response.setMessage("Required api parameters missing.");
				response.setStatus(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Error while capturing the automation details :: " + automationName);
			response.setAutomationName(automationName);
			response.setMessage("Error while capturing the metrics {} " +e.getMessage());
			response.setStatus(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
