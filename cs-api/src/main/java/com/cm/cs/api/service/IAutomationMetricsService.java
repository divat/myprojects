package com.cm.cs.api.service;

import java.text.ParseException;
import java.util.List;

import com.cm.cs.api.model.AutomationJobs;
import com.cm.cs.api.model.AutomationMetrics;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.ClientDivisionReport;
import com.cm.cs.api.model.ProductionReportForm;
import com.cm.cs.api.model.ProductionReportMetrics;
import com.cm.cs.api.model.ToolMetrics;

public interface IAutomationMetricsService {

	AutomationMetrics saveAutomcationMetrics(AutomationTools automation, String jobId, String pageCount,String jobType);
	
	List<ToolMetrics> getAutomationMetrics();
	
	List<AutomationJobs> automationJobList(Long automationId);
	
	List<AutomationJobs> automationJobReport();
	
	List<ClientDivisionReport> DivisionClientReport(ProductionReportForm reportForm) throws ParseException;
	 
	List<ClientDivisionReport> AllclientAllDivisionReportMetrics(ProductionReportForm reportForm) throws ParseException;
	
	List<ClientDivisionReport> AllDivisionClientReport(ProductionReportForm reportForm) throws ParseException;
	
	List<ClientDivisionReport> DivisionAllClientReport(ProductionReportForm reportForm) throws ParseException;
}
