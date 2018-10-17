
package com.cm.cs.api.service;

import java.util.List;

import com.cm.cs.api.model.AutomationTools;

public interface IAutomationSerivce {

	AutomationTools findByAutomationDetails(String automationName, String pageCount, String jobId, String jobType);
	
	List<AutomationTools> loadAutomationTools(String automationName);
	
	AutomationTools findByAutomationInputs(String automationName, String division, String manualMetrics, String manualPages, String automationMetrics, String automationPages, String clientName, String toolDescription);
	
	List<AutomationTools> automationDetailsList();
	
	AutomationTools findByAutomationId(Long automationId);
}
