package com.cm.cs.api.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cm.cs.api.config.hibernate.Dao;
import com.cm.cs.api.model.AutomationJobs;
import com.cm.cs.api.model.AutomationMetrics;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.ClientDivisionReport;
import com.cm.cs.api.model.ProductionReportForm;
import com.cm.cs.api.model.ProductionReportMetrics;
import com.cm.cs.api.model.ToolMetrics;

/**
 * 
 * @author DHIVAKART
 *
 */
@Repository
@Transactional
public class AutomationMetricsDao extends Dao<AutomationMetrics>{

	private static final SimpleDateFormat sdfIn = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final String AUTOMATION_METRICS_SQL="select t.automation_tool_id,t.automation_tool_name,c.client_name,d.division_name,t.manual_pages_count,t.manual_metrics, "
			+ "t.automation_pages_count,t.automation_metrics,t.created_on,sum(ame.page_or_image_count) as prod_num_of_pages,(t.manual_metrics/t.manual_pages_count) * sum(ame.page_or_image_count) as manualmetrics,"
			+ "(t.automation_metrics/t.automation_pages_count) * sum(ame.page_or_image_count) as automationmetrics "
			+ "from mast_cs_automation_tools t "
			+ "inner join mast_client_tools_mapping ct on(ct.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_client c on(c.client_id=ct.client_id) "
			+ "inner join mast_division_tools_mapping dt on(dt.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_division d on(d.division_id=dt.division_id) "
			+ "inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) "
			+ "where t.is_active=true "
			+ "group by t.automation_tool_name,c.client_name,d.division_name,t.manual_pages_count,"
			+ "t.manual_metrics,t.automation_pages_count,t.automation_metrics,t.created_on,t.automation_tool_id";
	
	private static final String AUTOMATION_JOBS_SQL = "select ame.job_id, ame.job_type, ame.job_created_on, ame.page_or_image_count "
			+ "from mast_cs_automation_tools t inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) where t.is_active=true and t.automation_tool_id=:automationId";
	
	private static final String AUTOMATION_JOB_REPORT_SQL = "select t.automation_tool_name,ame.job_id,ame.job_type,ame.page_or_image_count,ame.job_created_on from "
			+ "mast_cs_automation_tools t inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) where t.is_active=true";
	
	private static final String PRODUCTION_METRICS_REPORT_SQL = "select division_name,client_name,sum(prod_num_of_pages) as pages,sum(projected_prod_page_mm) as mm,sum(projected_prod_page_am) as am "
			+ "from (select c.client_name,d.division_name,t.created_on,sum(ame.page_or_image_count) as prod_num_of_pages,round((t.manual_metrics/t.manual_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_mm, "
			+ "round((t.automation_metrics/t.automation_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_am "
			+ "from mast_cs_automation_tools t "
			+ "inner join mast_client_tools_mapping ct on(ct.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_client c on(c.client_id=ct.client_id) "
			+ "inner join mast_division_tools_mapping dt on(dt.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_division d on(d.division_id=dt.division_id) "
			+ "inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) "
			+ "where "
			+ "d.division_id = :divisionId and c.client_id = :clientId and t.is_active=true and to_char(ame.job_created_on, 'dd/mm/yyyy') >= :fromDate and to_char(ame.job_created_on, 'dd/mm/yyyy') <= :toDate "
			+ "group by c.client_name,d.division_name,t.manual_pages_count,t.manual_metrics,t.automation_pages_count, "
			+ "t.automation_metrics,t.created_on,t.automation_tool_id) as d group by client_name, "
			+ "division_name";
	
	private static final String PRODUCTION_ALL_CLIENT_ALL_DIVISION_REPORT_SQL = "select division_name,client_name,sum(prod_num_of_pages) as pagecount,sum(projected_prod_page_mm) as mm,sum(projected_prod_page_am) as am from "
			+ "(select c.client_name,d.division_name,t.created_on,sum(ame.page_or_image_count) as prod_num_of_pages,round((t.manual_metrics/t.manual_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_mm, "
			+ "round((t.automation_metrics/t.automation_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_am from "
			+ "mast_cs_automation_tools t inner join mast_client_tools_mapping ct on(ct.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_client c on(c.client_id=ct.client_id) "
			+ "inner join mast_division_tools_mapping dt on(dt.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_division d on(d.division_id=dt.division_id) "
			+ "inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) "
			+ "where t.is_active=true and to_char(ame.job_created_on, 'dd/mm/yyyy') >= :fromDate and to_char(ame.job_created_on, 'dd/mm/yyyy') <= :toDate "
			+ "group by c.client_name,d.division_name,t.manual_pages_count,t.manual_metrics,t.automation_pages_count,t.automation_metrics,t.created_on, t.automation_tool_id) as d "
			+ "group by client_name,division_name";
	
	private static final String PRODUCTION_CLIENT_ALL_DIVISION_REPORT_SQL= "select division_name,client_name,sum(prod_num_of_pages) as pages,sum(projected_prod_page_mm) as mm,sum(projected_prod_page_am) as am from "
			+ "(select c.client_name,d.division_name,t.created_on,sum(ame.page_or_image_count) as prod_num_of_pages,round((t.manual_metrics/t.manual_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_mm,round((t.automation_metrics/t.automation_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_am "
			+ "from mast_cs_automation_tools t "
			+ "inner join mast_client_tools_mapping ct on(ct.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_client c on(c.client_id=ct.client_id) "
			+ "inner join mast_division_tools_mapping dt on(dt.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_division d on(d.division_id=dt.division_id) "
			+ "inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) "
			+ "where c.client_id = :clientId and t.is_active=true and to_char(ame.job_created_on, 'dd/mm/yyyy') >= :fromDate and to_char(ame.job_created_on, 'dd/mm/yyyy') <= :toDate "
			+ "group by c.client_name,d.division_name,t.manual_pages_count,t.manual_metrics,t.automation_pages_count,t.automation_metrics, "
			+ "t.created_on,t.automation_tool_id) as d group by client_name, division_name";
	
	private static final String PRODUCTION_ALL_CLIENT_DIVISION_REPORT_SQL = "select division_name,client_name,sum(prod_num_of_pages) as pages,sum(projected_prod_page_mm) as mm,sum(projected_prod_page_am) as am "
			+ "from(select c.client_name,d.division_name,t.created_on,sum(ame.page_or_image_count) as prod_num_of_pages,round((t.manual_metrics/t.manual_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_mm, "
			+ "round((t.automation_metrics/t.automation_pages_count) * sum(ame.page_or_image_count)) as projected_prod_page_am "
			+ "from mast_cs_automation_tools t "
			+ "inner join mast_client_tools_mapping ct on(ct.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_client c on(c.client_id=ct.client_id) "
			+ "inner join mast_division_tools_mapping dt on(dt.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_cs_division d on(d.division_id=dt.division_id) "
			+ "inner join mast_automation_tools_input_mapping am on(am.automation_tool_id=t.automation_tool_id) "
			+ "inner join mast_automation_metrics ame on(ame.id=am.id) "
			+ "where d.division_id = :divisionId and t.is_active=true and to_char(ame.job_created_on, 'dd/mm/yyyy') >= :fromDate and to_char(ame.job_created_on, 'dd/mm/yyyy') <= :toDate "
			+ "group by c.client_name,d.division_name,t.manual_pages_count,t.manual_metrics,t.automation_pages_count,t.automation_metrics, "
			+ "t.created_on,t.automation_tool_id) as d group by client_name,division_name";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public AutomationMetricsDao(){
		super(AutomationMetrics.class);
	}
	
	/**
	 * Find the metrics by automation tools 
	 * and job id
	 * @param automation
	 * @param jobId
	 * @return
	 */
	public AutomationMetrics findByAutomationAndJobId(AutomationTools automation, String jobId){
		AutomationMetrics metrics = (AutomationMetrics) getSession().createQuery("from AutomationMetrics m where m.jobId = :jobId and m.automationToolDetails.id = " +automation.getId())
				.setParameter("jobId", jobId)
				.uniqueResult();
		return metrics;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ToolMetrics> getAutomationMetrics(){
		SQLQuery query = getSession().createSQLQuery(AUTOMATION_METRICS_SQL);
		List<Object[]> data = query.list();
		
		List<ToolMetrics> metricsList = new ArrayList<ToolMetrics>();
		
		ToolMetrics toolMetrics = null;
		for(Object[] row : data){
			toolMetrics = new ToolMetrics();
			toolMetrics.setAutomationToolId(row[0].toString());
			toolMetrics.setAutomationName(row[1].toString());
			toolMetrics.setClientName(row[2].toString());
			toolMetrics.setDivisionName(row[3].toString());
			toolMetrics.setManualPagesCount(Integer.valueOf(row[4].toString()));
			toolMetrics.setManualMetrics(Double.valueOf(row[5].toString()));
			toolMetrics.setAutomationPagesCount(Integer.valueOf(row[6].toString()));
			toolMetrics.setAutomationMetrics(Double.valueOf(row[7].toString()));
			toolMetrics.setCreatedOn(row[8].toString());
			toolMetrics.setProdNoOfPages(Integer.valueOf(row[9].toString()));
			//System.out.println("====" +Double.valueOf(row[10].toString()));
			toolMetrics.setProjectedManualMetrics(Double.valueOf(row[10].toString()));
			
			toolMetrics.setProjectedAutomationMetrics(Double.valueOf(row[11].toString()));
			
			metricsList.add(toolMetrics);
		}
		return metricsList;
	}
	
	/**
	 * 
	 * @param automationToolId
	 * @return
	 */
	public List<AutomationJobs> automationJobList(Long automationToolId){
		SQLQuery query = getSession().createSQLQuery(AUTOMATION_JOBS_SQL);
		query.setLong("automationId", automationToolId);
		List<Object[]> data = query.list();
		
		List<AutomationJobs> automationJobList = new ArrayList<AutomationJobs>();
		AutomationJobs jobs = null;
		for(Object[] row : data){
			jobs = new AutomationJobs();
			jobs.setJobId(row[0].toString());
			jobs.setJobType(row[1].toString());
			jobs.setJobCreatedOn(row[2].toString());
			jobs.setPageOrImageCount(Integer.valueOf(row[3].toString()));
			automationJobList.add(jobs);
		}
		return automationJobList;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<AutomationJobs> automationJobReportList(){
		SQLQuery query = getSession().createSQLQuery(AUTOMATION_JOB_REPORT_SQL);
		//query.setLong("automationId", automationToolId);
		List<Object[]> data = query.list();
		
		List<AutomationJobs> automationJobList = new ArrayList<AutomationJobs>();
		AutomationJobs jobs = null;
		for(Object[] row : data){
			jobs = new AutomationJobs();
			jobs.setAutomationName(row[0].toString());
			jobs.setJobId(row[1].toString());
			jobs.setJobType(row[2].toString());
			jobs.setPageOrImageCount(Integer.valueOf(row[3].toString()));
			jobs.setJobCreatedOn(row[4].toString());
			automationJobList.add(jobs);
		}
		return automationJobList;
	}
	
	/**
	 * 
	 * @param reportForm
	 * @return
	 * @throws ParseException 
	 */
	public List<ClientDivisionReport> DivisionClientReport(ProductionReportForm reportForm) throws ParseException{
		
		Date inputFromDate = sdfIn.parse(reportForm.getFromDate());
		String fromDate = sdfOut.format(inputFromDate);
		Date inputToDate = sdfIn.parse(reportForm.getToDate());
		String toDate = sdfOut.format(inputToDate);
		
		SQLQuery query = getSession().createSQLQuery(PRODUCTION_METRICS_REPORT_SQL);
		query.setLong("divisionId", Long.valueOf(reportForm.getDivision()));
		query.setLong("clientId", Long.valueOf(reportForm.getClient()));
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		
		
		List<Object[]> data = query.list();
		
		List<ClientDivisionReport> productionReportMetrics = new ArrayList<ClientDivisionReport>();
		ClientDivisionReport prReportMetrics = null;
		for(Object[] row : data){
			prReportMetrics = new ClientDivisionReport();
			prReportMetrics.setDivisionName(row[0].toString());
			prReportMetrics.setClientName(row[1].toString());
			prReportMetrics.setNoOfPages(Integer.valueOf(row[2].toString()));
			prReportMetrics.setProjectedManuamMetrics(Double.valueOf(row[3].toString()));
			prReportMetrics.setProjectedAutomationMetrics(Double.valueOf(row[4].toString()));
			productionReportMetrics.add(prReportMetrics);
		}
		return productionReportMetrics;
	}
	
	/**
	 * 
	 * @param reportForm
	 * @return
	 * @throws ParseException
	 */
	public List<ClientDivisionReport> AllclientAllDivisionReportMetrics(ProductionReportForm reportForm) throws ParseException{
		Date inputFromDate = sdfIn.parse(reportForm.getFromDate());
		String fromDate = sdfOut.format(inputFromDate);
		Date inputToDate = sdfIn.parse(reportForm.getToDate());
		String toDate = sdfOut.format(inputToDate);
		
		SQLQuery query = getSession().createSQLQuery(PRODUCTION_ALL_CLIENT_ALL_DIVISION_REPORT_SQL);
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		
		List<Object[]> data = query.list();
		
		List<ClientDivisionReport> clientDivisionReportList = new ArrayList<ClientDivisionReport>();
		
		ClientDivisionReport report = null;
		for(Object row[] : data){
			report = new ClientDivisionReport();
			report.setDivisionName(row[0].toString());
			report.setClientName(row[1].toString());
			report.setNoOfPages(Integer.valueOf(row[2].toString()));
			report.setProjectedManuamMetrics(Double.valueOf(row[3].toString()));
			report.setProjectedAutomationMetrics(Double.valueOf(row[4].toString()));
			clientDivisionReportList.add(report);
		}
		return clientDivisionReportList;
	}
	
	/**
	 * All division and specific client report
	 * @param reportForm
	 * @return
	 * @throws ParseException
	 */
	public List<ClientDivisionReport> AllDivisionClientReport(ProductionReportForm reportForm) throws ParseException{
		Date inputFromDate = sdfIn.parse(reportForm.getFromDate());
		String fromDate = sdfOut.format(inputFromDate);
		Date inputToDate = sdfIn.parse(reportForm.getToDate());
		String toDate = sdfOut.format(inputToDate);
		
		SQLQuery query = getSession().createSQLQuery(PRODUCTION_CLIENT_ALL_DIVISION_REPORT_SQL);
		query.setLong("clientId", Long.valueOf(reportForm.getClient()));
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		
		List<Object[]> data = query.list();
		List<ClientDivisionReport> list = new ArrayList<ClientDivisionReport>();
		
		ClientDivisionReport report = null;
		for(Object row[] : data){
			report = new ClientDivisionReport();
			report.setDivisionName(row[0].toString());
			report.setClientName(row[1].toString());
			report.setNoOfPages(Integer.valueOf(row[2].toString()));
			report.setProjectedManuamMetrics(Double.valueOf(row[3].toString()));
			report.setProjectedAutomationMetrics(Double.valueOf(row[4].toString()));
			list.add(report);
		}
		return list;
	}
	
	/**
	 * All division and specific client report
	 * @param reportForm
	 * @return
	 * @throws ParseException
	 */
	public List<ClientDivisionReport> DivisionAllClientReport(ProductionReportForm reportForm) throws ParseException{
		Date inputFromDate = sdfIn.parse(reportForm.getFromDate());
		String fromDate = sdfOut.format(inputFromDate);
		Date inputToDate = sdfIn.parse(reportForm.getToDate());
		String toDate = sdfOut.format(inputToDate);
		
		SQLQuery query = getSession().createSQLQuery(PRODUCTION_ALL_CLIENT_DIVISION_REPORT_SQL);
		query.setLong("divisionId", Long.valueOf(reportForm.getDivision()));
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		
		List<Object[]> data = query.list();
		List<ClientDivisionReport> list = new ArrayList<ClientDivisionReport>();
		
		ClientDivisionReport report = null;
		for(Object row[] : data){
			report = new ClientDivisionReport();
			report.setDivisionName(row[0].toString());
			report.setClientName(row[1].toString());
			report.setNoOfPages(Integer.valueOf(row[2].toString()));
			report.setProjectedManuamMetrics(Double.valueOf(row[3].toString()));
			report.setProjectedAutomationMetrics(Double.valueOf(row[4].toString()));
			list.add(report);
		}
		return list;
	}
}
