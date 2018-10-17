package com.cm.cs.api.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.cs.api.dao.AutomationToolsDao;
import com.cm.cs.api.dao.ClientDao;
import com.cm.cs.api.dao.DivisionDao;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.Division;
import com.cm.cs.api.service.IAutomationSerivce;

import scala.annotation.meta.setter;

@Service
public class AutomationServiceImpl implements IAutomationSerivce{

	@Autowired
	private AutomationToolsDao automationToolsDao;
	
	@Autowired
	private DivisionDao divisionDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Override
	public AutomationTools findByAutomationDetails(String automationName, String pageCount, String jobId,
			String jobType) {
		AutomationTools automationDetails = null;
		try {
			automationDetails = automationToolsDao.findByStringValue("automationName", automationName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return automationDetails;
	}

	@Override
	public List<AutomationTools> loadAutomationTools(String automationName) {
		return automationToolsDao.findByString("automationName", automationName);
	}

	@Override
	public AutomationTools findByAutomationInputs(String automationName, String division, String manualMetrics,
			String manualPages, String automationMetrics, String automationPages, String clientName, String toolDescription) {
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		try {
			Division divisionObj = divisionDao.findById(Long.valueOf(division));
			Client client = clientDao.findById(Long.valueOf(clientName));
			if(divisionObj != null & client != null){
				
				//Client client = clientDao.findClientByDivision(clientName, divisionObj);
				/*Client client = clientDao.findById(Long.valueOf(clientName));
				if(client != null){
					//Map the client for division
					Division divisionDetails = divisionDao.findClientByDivision(client, divisionObj);
					if(divisionDetails != null){
						System.out.println("Division details exists");
						
					}else{
						//Map the division with client
						Set<Division> div = new HashSet<Division>();
						div.add(divisionObj);
						client.setClientDivisionMapping(div);
						
						client = clientDao.save(client);
						if(client != null){
							System.out.println("Client details mapped");
						}
					}
				}*/
				
				AutomationTools automationDetails = automationToolsDao.findByAutomationInputs(automationName, divisionObj);
				if(automationDetails != null){
					//Update the automation details
					automationDetails.setManualNoOfPages(Integer.valueOf(manualPages));
					automationDetails.setManualMetrics(Double.valueOf(manualMetrics));
					automationDetails.setAutomationNoOfPages(Integer.valueOf(automationPages));
					automationDetails.setAutomationMetrics(Double.valueOf(automationMetrics));
					automationDetails.setDivision(divisionObj);
					automationDetails.setClient(client);
					automationDetails.setActive(true);
					automationDetails.setModifiedOn(date);
					automationDetails.setToolDescription(toolDescription);
					automationDetails = automationToolsDao.update(automationDetails);
					return automationDetails;
				}else{
					AutomationTools automation = new AutomationTools();
					automation.setAutomationName(automationName);
					automation.setManualNoOfPages(Integer.valueOf(manualPages));
					automation.setManualMetrics(Double.valueOf(manualMetrics));
					automation.setAutomationNoOfPages(Integer.valueOf(automationPages));
					automation.setAutomationMetrics(Double.valueOf(automationMetrics));
					automation.setDivision(divisionObj);
					automation.setClient(client);
					automation.setActive(true);
					automation.setCreatedOn(date);
					automation.setToolDescription(toolDescription);
					automation = automationToolsDao.save(automation);
					return automation;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<AutomationTools> automationDetailsList() {		
		return automationToolsDao.automationDetailsList();
	}

	@Override
	public AutomationTools findByAutomationId(Long automationId) {
		return automationToolsDao.findById(automationId);
	}
	
	/*private Division saveDivisionDetails(String divisionName){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		Division division = null;
		try {
			division = new Division();
			division.setDivisionName(divisionName);
			division.setActive(true);
			division.setCreatedOn(date);
			//division.set
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Add the new automation details
	 * @param projectName
	 * @param clientName
	 * @param automationName
	 * @param developerName
	 * @param division
	 * @return
	 *//*
	private AutomationTools saveAutomationDetails(String projectName, String clientName, String automationName, String developerName, Division division){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		AutomationTools automationTools = null;
		try {
			automationTools = new AutomationTools();
			automationTools.setProjectName(projectName);
			automationTools.setClientName(clientName);
			automationTools.setAutomationName(automationName);
			automationTools.setCreatedBy(developerName);
			automationTools.setActive(true);
			automationTools.setCreatedOn(date);
			automationTools.setDivision(division);
			automationTools.setReleaseDate(date);
			automationToolsDao.save(automationTools);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return automationTools;
	}*/

}
