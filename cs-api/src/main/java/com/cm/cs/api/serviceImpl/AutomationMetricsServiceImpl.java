package com.cm.cs.api.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.cs.api.dao.AutomationMetricsDao;
import com.cm.cs.api.model.AutomationJobs;
import com.cm.cs.api.model.AutomationMetrics;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.ClientDivisionReport;
import com.cm.cs.api.model.ProductionReportForm;
import com.cm.cs.api.model.ProductionReportMetrics;
import com.cm.cs.api.model.ToolMetrics;
import com.cm.cs.api.service.IAutomationMetricsService;

@Service
public class AutomationMetricsServiceImpl implements IAutomationMetricsService{

	@Autowired
	AutomationMetricsDao automationMetricsDao;
	
	@Override
	public AutomationMetrics saveAutomcationMetrics(AutomationTools automation, String jobId, String pageCount, String jobType) {
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		AutomationMetrics metrics = null;
		try {
			
			metrics = automationMetricsDao.findByAutomationAndJobId(automation, jobId);
			if(metrics != null){
				//update the existing metrics
				//metrics.setPageOrImageCount(Integer.valueOf(pageCount)+metrics.getPageOrImageCount());
				metrics.setPageOrImageCount(Integer.valueOf(pageCount));
				metrics.setJobType(jobType);
				metrics.setAutomationToolDetails(automation);
				metrics.setModifiedOn(date);
				automationMetricsDao.update(metrics);
			}else{
				metrics = new AutomationMetrics();
				metrics.setJobId(jobId);
				metrics.setPageOrImageCount(Integer.valueOf(pageCount));
				metrics.setJobType(jobType);
				metrics.setAutomationToolDetails(automation);
				metrics.setCreatedOn(date);
				metrics = automationMetricsDao.save(metrics);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metrics;
	}

	@Override
	public List<ToolMetrics> getAutomationMetrics() {
		return automationMetricsDao.getAutomationMetrics();
	}

	@Override
	public List<AutomationJobs> automationJobList(Long automationId) {
		return automationMetricsDao.automationJobList(automationId);
	}

	@Override
	public List<AutomationJobs> automationJobReport() {
		return automationMetricsDao.automationJobReportList();
	}

	@Override
	public List<ClientDivisionReport> DivisionClientReport(ProductionReportForm reportForm) throws ParseException {
		return automationMetricsDao.DivisionClientReport(reportForm);
	}

	@Override
	public List<ClientDivisionReport> AllclientAllDivisionReportMetrics(ProductionReportForm reportForm)
			throws ParseException {
		return automationMetricsDao.AllclientAllDivisionReportMetrics(reportForm);
	}

	@Override
	public List<ClientDivisionReport> AllDivisionClientReport(ProductionReportForm reportForm) throws ParseException {
		return automationMetricsDao.AllDivisionClientReport(reportForm);
	}

	@Override
	public List<ClientDivisionReport> DivisionAllClientReport(ProductionReportForm reportForm) throws ParseException {
		return automationMetricsDao.DivisionAllClientReport(reportForm);
	}
}