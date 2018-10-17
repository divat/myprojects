package com.cm.cs.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientDivisionReport;
import com.cm.cs.api.model.Division;
import com.cm.cs.api.model.ProductionReportForm;
import com.cm.cs.api.model.ProductionReportMetrics;
import com.cm.cs.api.service.IAutomationMetricsService;
import com.cm.cs.api.service.IClientService;
import com.cm.cs.api.service.IDivisionService;

/**
 * 
 * @author DHIVAKART
 *
 */
@Controller
public class ReportController {

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	IDivisionService divisionService;
	
	@Autowired
	IClientService clientService;
	
	@Autowired
	IAutomationMetricsService automationMetricsService;
	
	@Value("${metrics.report.path}")
	private String automationMetricsToolPath;
	
	private InputStream fileInputStream;

	private String fileName;
	
	private static final int BUFFER_SIZE = 4096;
	
	@ModelAttribute("divisionList")
	public List<Division> divisionList(){
		List<Division> divisionList = new ArrayList<Division>();
		divisionList = divisionService.divisionList();
		return divisionList;
	}
	
	@ModelAttribute("clientList")
	public List<Client> clientList(){
		List<Client> clientList = new ArrayList<Client>();
		clientList = clientService.clientList();
		return clientList;
	}
	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/getProductionReport")
	public ModelAndView getProductionReport(ModelAndView model){
		return new ModelAndView("production-report", "productionReportForm", new ProductionReportForm());
	}
	
	/**
	 * Process production report
	 * @param productionReportForm
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/productionReport")
	public void processProductionReport(@ModelAttribute ProductionReportForm productionReportForm, BindingResult result, Model model, HttpServletResponse response){
		log.info("Processing production report for {} " +productionReportForm.getClient() +"::"+ productionReportForm.getDivision());
		try {
			/*if(result.hasErrors()){
				return new ModelAndView("production-report");
			}*/
			
			if(productionReportForm.getClient() != null && productionReportForm.getDivision() != null && productionReportForm.getFromDate() != null && productionReportForm.getToDate() != null){
				
				if(productionReportForm.getDivision().equals("0") && productionReportForm.getClient().equals("0")){
					//Division level report for all clients.
					List<ClientDivisionReport> clientDivsionReportList = automationMetricsService.AllclientAllDivisionReportMetrics(productionReportForm);
					processClientDivisionReport(clientDivsionReportList, response);
				}else if(productionReportForm.getDivision().equals("0") && !productionReportForm.getClient().equals("0")){
					//All division with corresponding client report
					List<ClientDivisionReport> clientDivisionReportList = automationMetricsService.AllDivisionClientReport(productionReportForm);
					processAllDivisionClientReport(clientDivisionReportList, response);
				}else if(!productionReportForm.getDivision().equals("0") && productionReportForm.getClient().equals("0")){
					//Particular division with all clients report
					List<ClientDivisionReport> clientDivisionReportList = automationMetricsService.DivisionAllClientReport(productionReportForm);
					processDivisionAllClientReport(clientDivisionReportList, response);
				}else{
					List<ClientDivisionReport> productionReportMetricsList = automationMetricsService.DivisionClientReport(productionReportForm);
					
					 HSSFWorkbook workbook = new HSSFWorkbook();
					 HSSFSheet sheet = workbook.createSheet("Automation-Metrics");
					 HSSFRow rowhead = sheet.createRow((short) 0);
					 rowhead.createCell((short) 0).setCellValue("Client");
					 rowhead.createCell((short) 1).setCellValue("Division");
					/* rowhead.createCell((short) 2).setCellValue("Automation Name");*/
					 /*rowhead.createCell((int) 2).setCellValue("Created On");*/
					 rowhead.createCell((int) 2).setCellValue("Production Page Count");
					 rowhead.createCell((short) 3).setCellValue("Actual Manual Metrics");
					 rowhead.createCell((short) 4).setCellValue("Actual Automation Metrics");
					 rowhead.createCell((short) 5).setCellValue("Automation Saved Metrics");
					 
					 int i = 1;
					 int rowCount = 0;
					 int manualMetricsHours = 0;
					 int manualMetricsMins = 0;
					 int automationMetricsHours = 0;
					 int automationMetricsMins = 0;
					 int savedAutomationMetricHrs = 0;
					 int savedAutomationMetricMins = 0;
					 for(ClientDivisionReport rs : productionReportMetricsList){
						//System.out.println("=======" +rs.getAutomationToolName());
						HSSFRow row = sheet.createRow((short) i);
				        row.createCell((short) 0).setCellValue(rs.getClientName());
				        row.createCell((short) 1).setCellValue(rs.getDivisionName());
				        /*row.createCell((short) 2).setCellValue(rs.getAutomationToolName());*/
				        /*row.createCell((short) 3).setCellValue(rs.getCreatedOn());*/
				        row.createCell((int) 2).setCellValue(rs.getNoOfPages());
				        
				        
				        Integer mmHours = 0;
						Integer mmMinutes = 0;
						String mm = null;
						int mmins = 0;
						int amins = 0;
						if(rs.getProjectedManuamMetrics() > 60){
							mmHours = (int) (rs.getProjectedManuamMetrics() / 60);
							mmMinutes = (int) (rs.getProjectedManuamMetrics()  % 60);
							mm = mmHours +" hr "+ mmMinutes + " mins"; 
							row.createCell((short) 3).setCellValue(mm);
						}else{
							mmins = (int) Math.round(rs.getProjectedManuamMetrics());
							mm = String.format("%.0f",rs.getProjectedManuamMetrics()) + " mins";
							row.createCell((short) 3).setCellValue(mm);
						}
						
						manualMetricsHours =  manualMetricsHours + mmHours;
						manualMetricsMins = manualMetricsMins + mmMinutes + mmins;
						
						Integer amHours = 0;
						Integer amMinutes = 0;
						String am = null;
						
						//Saved hours
						int savedAutomationHours = 0;
						int savedAutomationMins = 0;
						if(rs.getProjectedAutomationMetrics() > 60){
							amHours = (int) (rs.getProjectedAutomationMetrics() / 60);
							amMinutes = (int) (rs.getProjectedAutomationMetrics() % 60);
							am = amHours +" hr "+ amMinutes + " mins"; 
							row.createCell((short) 4).setCellValue(am);
						}else{
							amins = (int) Math.round(rs.getProjectedAutomationMetrics());
							am = String.format("%.0f",rs.getProjectedAutomationMetrics()) + " mins";
							row.createCell((short) 4).setCellValue(am);
						}
				        
						automationMetricsHours = automationMetricsHours + amHours;
						automationMetricsMins = automationMetricsMins + amMinutes + amins;
						
						if(mmHours > amHours){
							savedAutomationHours = mmHours-amHours;
						}else{
							savedAutomationHours = mmHours-amHours;
						}
						
						
						if(mmMinutes > amMinutes){
							savedAutomationMins = mmMinutes - amMinutes;
						}else{
							savedAutomationMins = amMinutes - mmMinutes;
						}
						
						savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationHours;
						savedAutomationMetricMins = savedAutomationMetricMins + savedAutomationMins;
						
						if(savedAutomationHours != 0){
							if(savedAutomationMins != 0){
								System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
								row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
							}else{
								System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs ");
								row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs ");
							}
							
						}else{
							System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" + savedAutomationMins +" mins");
							row.createCell((short) 5).setCellValue(savedAutomationMins+" mins ");
						}
						
				        i++;
					 }
					 
					 //System.out.println("Consolidated MM Hrs :: " +manualMetricsHours);
					 //System.out.println("Consolidated MM Mins conv hr :: " +manualMetricsMins/60);
					 //System.out.println("Consolidated MM Mins :: " +manualMetricsMins%60);
					 String projectedMMHrs = String.valueOf(manualMetricsHours + manualMetricsMins/60);
					 String projectedMMMins = String.valueOf(manualMetricsMins % 60);
					 
					 /*Integer savedMMHrs = Integer.valueOf(manualMetricsHours + manualMetricsMins/60);
					 Integer savedMMMins = Integer.valueOf(manualMetricsMins % 60);*/
					 
					 String projectedAMHrs = String.valueOf(automationMetricsHours + automationMetricsMins/60);
					 String projectedAMMins = String.valueOf(automationMetricsMins % 60);
					 
					 /*Integer savedAMHrs = Integer.valueOf(automationMetricsHours + automationMetricsMins/60);
					 Integer savedAMMins = Integer.valueOf(automationMetricsMins % 60);*/
					 
					 /*Integer a = savedMMHrs - savedAMHrs;
					 Integer b = savedMMMins - savedAMMins;*/
					 //System.out.println("======" +a+" hrs " +b+ " mins");
					 
					 savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationMetricMins/60;
					 savedAutomationMetricMins = savedAutomationMetricMins % 60;
					 String savedHrs = String.valueOf(savedAutomationMetricHrs +" hrs " +savedAutomationMetricMins+ " mins");
					 
					 Row pageTotal = sheet.createRow(i + 1);
					 Cell pageTotalText = pageTotal.createCell(1);
					 pageTotalText.setCellValue("Total ::");
				     
				     //System.out.println("==="+pageTotal.getRowNum());
				     
				     Cell totalPagesCell = pageTotal.createCell(2);
				     totalPagesCell.setCellFormula("SUM(C2:C" + String.valueOf(pageTotal.getRowNum()-1) + ")");
				     
				     Cell manualMetricsCell = pageTotal.createCell(3);
				     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
				     manualMetricsCell.setCellValue(projectedMMHrs+" hrs " +projectedMMMins + " mins");
				     
				     Cell automationMetricsCell = pageTotal.createCell(4);
				     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
				     automationMetricsCell.setCellValue(projectedAMHrs+" hrs " +projectedAMMins + " mins");
				     
				     Cell savedMetricsCell = pageTotal.createCell(5);
				     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
				     savedMetricsCell.setCellValue(savedHrs);
					 
					 String yemi = automationMetricsToolPath;
					    
					    File file = new File(yemi);
					    if(file.exists()){
					    	file.delete();
					    	FileOutputStream fileOut = new FileOutputStream(file);
						    workbook.write(fileOut);
						    fileOut.close();
					    }else{
					    	FileOutputStream fileOut = new FileOutputStream(file);
						    workbook.write(fileOut);
						    fileOut.close();
					    }
					    
					    this.setFileName("Production-Metrics-report.xls");
					    fileInputStream = new FileInputStream(new File(automationMetricsToolPath));
					    
					    response.setContentType("application/octet-stream");
						response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
						
						if(fileInputStream != null){
							OutputStream outputStream = response.getOutputStream();
							int bytesRead = -1;
							byte[] buffer = new byte[BUFFER_SIZE];
					        // write bytes read from the input stream into the output stream
					        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
					        	outputStream.write(buffer, 0, bytesRead);
					        }
					 
					        outputStream.close();
						}
					
					return;
				}
			}else{
				model.addAttribute("responseMsg", "Enter the mandatory fields.");
			}
		} catch (Exception e) {
			log.error("Error while processing the report :: " +e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ALL - Client division report
	 * @param clientDivisionReport
	 * @throws IOException 
	 */
	private void processClientDivisionReport(List<ClientDivisionReport> clientDivisionReport, HttpServletResponse response) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("Automation-Metrics");
		 HSSFRow rowhead = sheet.createRow((short) 0);
		 rowhead.createCell((short) 0).setCellValue("Division");
		 rowhead.createCell((short) 1).setCellValue("Client");
		 rowhead.createCell((int) 2).setCellValue("Production Page Count");
		 rowhead.createCell((short) 3).setCellValue("Actual Manual Metrics");
		 rowhead.createCell((short) 4).setCellValue("Actual Automation Metrics");
		 rowhead.createCell((short) 5).setCellValue("Automation Saved Metrics");
		 
		 int i = 1;
		 int rowCount = 0;
		 int manualMetricsHours = 0;
		 int manualMetricsMins = 0;
		 int automationMetricsHours = 0;
		 int automationMetricsMins = 0;
		 int savedAutomationMetricHrs = 0;
		 int savedAutomationMetricMins = 0;
		 for(ClientDivisionReport rs : clientDivisionReport){
			//System.out.println("=======" +rs.getAutomationToolName());
			HSSFRow row = sheet.createRow((short) i);
	        row.createCell((short) 0).setCellValue(rs.getDivisionName());
	        row.createCell((short) 1).setCellValue(rs.getClientName());
	        row.createCell((int) 2).setCellValue(rs.getNoOfPages());
	        /*row.createCell((short) 3).setCellValue(rs.getProjectedManuamMetrics());
	        row.createCell((int) 4).setCellValue(rs.getProjectedAutomationMetrics());*/
	        
	        
	        Integer mmHours = 0;
			Integer mmMinutes = 0;
			String mm = null;
			int mmins = 0;
			int amins = 0;
			
			 //Saved hours
			int savedAutomationHours = 0;
			int savedAutomationMins = 0;
			if(rs.getProjectedManuamMetrics() > 60){
				mmHours = (int) (rs.getProjectedManuamMetrics() / 60);
				mmMinutes = (int) (rs.getProjectedManuamMetrics()  % 60);
				mm = mmHours +" hr "+ mmMinutes + " mins"; 
				row.createCell((short) 3).setCellValue(mm);
			}else{
				mmins = (int) Math.round(rs.getProjectedManuamMetrics());
				mm = String.format("%.0f",rs.getProjectedManuamMetrics()) + " mins";
				row.createCell((short) 3).setCellValue(mm);
			}
			
			manualMetricsHours =  manualMetricsHours + mmHours;
			manualMetricsMins = manualMetricsMins + mmMinutes + mmins;
			
			Integer amHours = 0;
			Integer amMinutes = 0;
			String am = null;
			if(rs.getProjectedAutomationMetrics() > 60){
				amHours = (int) (rs.getProjectedAutomationMetrics() / 60);
				amMinutes = (int) (rs.getProjectedAutomationMetrics() % 60);
				am = amHours +" hr "+ amMinutes + " mins"; 
				row.createCell((short) 4).setCellValue(am);
			}else{
				amins = (int) Math.round(rs.getProjectedAutomationMetrics());
				am = String.format("%.0f",rs.getProjectedAutomationMetrics()) + " mins";
				row.createCell((short) 4).setCellValue(am);
			}
	        
			automationMetricsHours = automationMetricsHours + amHours;
			automationMetricsMins = automationMetricsMins + amMinutes + amins;
			
			
			if(mmHours > amHours){
				savedAutomationHours = mmHours-amHours;
			}else{
				savedAutomationHours = mmHours-amHours;
			}
			
			
			if(mmMinutes > amMinutes){
				savedAutomationMins = mmMinutes - amMinutes;
			}else{
				savedAutomationMins = amMinutes - mmMinutes;
			}
			
			savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationHours;
			savedAutomationMetricMins = savedAutomationMetricMins + savedAutomationMins;
			
			if(savedAutomationHours != 0){
				if(savedAutomationMins != 0){
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
				}else{
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs ");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs ");
				}
				
			}else{
				System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" + savedAutomationMins +" mins");
				row.createCell((short) 5).setCellValue(savedAutomationMins+" mins ");
			}
			
	        i++;
		 }
		 
		 //System.out.println("Consolidated MM Hrs :: " +manualMetricsHours);
		 //System.out.println("Consolidated MM Mins conv hr :: " +manualMetricsMins/60);
		 //System.out.println("Consolidated MM Mins :: " +manualMetricsMins%60);
		 String projectedMMHrs = String.valueOf(manualMetricsHours + manualMetricsMins/60);
		 String projectedMMMins = String.valueOf(manualMetricsMins % 60);
		 
		 String projectedAMHrs = String.valueOf(automationMetricsHours + automationMetricsMins/60);
		 String projectedAMMins = String.valueOf(automationMetricsMins % 60);
		 
		 savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationMetricMins/60;
		 savedAutomationMetricMins = savedAutomationMetricMins % 60;
		 String savedHrs = String.valueOf(savedAutomationMetricHrs +" hrs " +savedAutomationMetricMins+ " mins");
		 
		 Row pageTotal = sheet.createRow(i + 1);
		 Cell pageTotalText = pageTotal.createCell(1);
		 pageTotalText.setCellValue("Total ::");
	     
	     //System.out.println("==="+pageTotal.getRowNum());
	     
	     Cell totalPagesCell = pageTotal.createCell(2);
	     totalPagesCell.setCellFormula("SUM(C2:C" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     
	     Cell manualMetricsCell = pageTotal.createCell(3);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     manualMetricsCell.setCellValue(projectedMMHrs+" hrs " +projectedMMMins + " mins");
	     
	     Cell automationMetricsCell = pageTotal.createCell(4);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     automationMetricsCell.setCellValue(projectedAMHrs+" hrs " +projectedAMMins + " mins");
	     
	     Cell savedMetricsCell = pageTotal.createCell(5);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     savedMetricsCell.setCellValue(savedHrs);
		 
		 String yemi = automationMetricsToolPath;
		    
		    File file = new File(yemi);
		    if(file.exists()){
		    	file.delete();
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }else{
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }
		    
		    this.setFileName("Metrics-report.xls");
		    fileInputStream = new FileInputStream(new File(automationMetricsToolPath));
		    
		    response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
			
			if(fileInputStream != null){
				OutputStream outputStream = response.getOutputStream();
				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
		        // write bytes read from the input stream into the output stream
		        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
		        	outputStream.write(buffer, 0, bytesRead);
		        }
		 
		        outputStream.close();
			}
		
		return;
	}
	
	/**
	 * 
	 * @param clientDivisionReport
	 * @param response
	 * @throws IOException
	 */
	private void processAllDivisionClientReport(List<ClientDivisionReport> clientDivisionReport, HttpServletResponse response) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("Automation-Metrics");
		 HSSFRow rowhead = sheet.createRow((short) 0);
		 rowhead.createCell((short) 0).setCellValue("Division");
		 rowhead.createCell((short) 1).setCellValue("Client");
		 rowhead.createCell((int) 2).setCellValue("Production Page Count");
		 rowhead.createCell((short) 3).setCellValue("Actual Manual Metrics");
		 rowhead.createCell((short) 4).setCellValue("Actual Automation Metrics");
		 rowhead.createCell((short) 5).setCellValue("Automation Saved Metrics");
		 
		 int i = 1;
		 int rowCount = 0;
		 int manualMetricsHours = 0;
		 int manualMetricsMins = 0;
		 int automationMetricsHours = 0;
		 int automationMetricsMins = 0;
		 int savedAutomationMetricHrs = 0;
		 int savedAutomationMetricMins = 0;
		 for(ClientDivisionReport rs : clientDivisionReport){
			//System.out.println("=======" +rs.getAutomationToolName());
			HSSFRow row = sheet.createRow((short) i);
	        row.createCell((short) 0).setCellValue(rs.getDivisionName());
	        row.createCell((short) 1).setCellValue(rs.getClientName());
	        row.createCell((int) 2).setCellValue(rs.getNoOfPages());
	        /*row.createCell((short) 3).setCellValue(rs.getProjectedManuamMetrics());
	        row.createCell((int) 4).setCellValue(rs.getProjectedAutomationMetrics());*/
	        
	        
	        Integer mmHours = 0;
			Integer mmMinutes = 0;
			String mm = null;
			int mmins = 0;
			int amins = 0;
			 //Saved hours
			int savedAutomationHours = 0;
			int savedAutomationMins = 0;
			if(rs.getProjectedManuamMetrics() > 60){
				mmHours = (int) (rs.getProjectedManuamMetrics() / 60);
				mmMinutes = (int) (rs.getProjectedManuamMetrics()  % 60);
				mm = mmHours +" hr "+ mmMinutes + " mins"; 
				row.createCell((short) 3).setCellValue(mm);
			}else{
				mmins = (int) Math.round(rs.getProjectedManuamMetrics());
				mm = String.format("%.0f",rs.getProjectedManuamMetrics()) + " mins";
				row.createCell((short) 3).setCellValue(mm);
			}
			
			manualMetricsHours =  manualMetricsHours + mmHours;
			manualMetricsMins = manualMetricsMins + mmMinutes + mmins;
			
			Integer amHours = 0;
			Integer amMinutes = 0;
			String am = null;
			if(rs.getProjectedAutomationMetrics() > 60){
				amHours = (int) (rs.getProjectedAutomationMetrics() / 60);
				amMinutes = (int) (rs.getProjectedAutomationMetrics() % 60);
				am = amHours +" hr "+ amMinutes + " mins"; 
				row.createCell((short) 4).setCellValue(am);
			}else{
				amins = (int) Math.round(rs.getProjectedAutomationMetrics());
				am = String.format("%.0f",rs.getProjectedAutomationMetrics()) + " mins";
				row.createCell((short) 4).setCellValue(am);
			}
	        
			automationMetricsHours = automationMetricsHours + amHours;
			automationMetricsMins = automationMetricsMins + amMinutes + amins;
			
			if(mmHours > amHours){
				savedAutomationHours = mmHours-amHours;
			}else{
				savedAutomationHours = mmHours-amHours;
			}
			
			
			if(mmMinutes > amMinutes){
				savedAutomationMins = mmMinutes - amMinutes;
			}else{
				savedAutomationMins = amMinutes - mmMinutes;
			}
			
			savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationHours;
			savedAutomationMetricMins = savedAutomationMetricMins + savedAutomationMins;
			
			if(savedAutomationHours != 0){
				if(savedAutomationMins != 0){
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
				}else{
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs ");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs ");
				}
				
			}else{
				System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" + savedAutomationMins +" mins");
				row.createCell((short) 5).setCellValue(savedAutomationMins+" mins ");
			}
			
	        i++;
		 }
		 
		 //System.out.println("Consolidated MM Hrs :: " +manualMetricsHours);
		 //System.out.println("Consolidated MM Mins conv hr :: " +manualMetricsMins/60);
		 //System.out.println("Consolidated MM Mins :: " +manualMetricsMins%60);
		 String projectedMMHrs = String.valueOf(manualMetricsHours + manualMetricsMins/60);
		 String projectedMMMins = String.valueOf(manualMetricsMins % 60);
		 
		 String projectedAMHrs = String.valueOf(automationMetricsHours + automationMetricsMins/60);
		 String projectedAMMins = String.valueOf(automationMetricsMins % 60);
		 
		 savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationMetricMins/60;
		 savedAutomationMetricMins = savedAutomationMetricMins % 60;
		 String savedHrs = String.valueOf(savedAutomationMetricHrs +" hrs " +savedAutomationMetricMins+ " mins");
		 
		 Row pageTotal = sheet.createRow(i + 1);
		 Cell pageTotalText = pageTotal.createCell(1);
		 pageTotalText.setCellValue("Total ::");
	     
	     //System.out.println("==="+pageTotal.getRowNum());
	     
	     Cell totalPagesCell = pageTotal.createCell(2);
	     totalPagesCell.setCellFormula("SUM(C2:C" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     
	     Cell manualMetricsCell = pageTotal.createCell(3);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     manualMetricsCell.setCellValue(projectedMMHrs+" hrs " +projectedMMMins + " mins");
	     
	     Cell automationMetricsCell = pageTotal.createCell(4);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     automationMetricsCell.setCellValue(projectedAMHrs+" hrs " +projectedAMMins + " mins");
		 
	     Cell savedMetricsCell = pageTotal.createCell(5);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     savedMetricsCell.setCellValue(savedHrs);
	     
		 String yemi = automationMetricsToolPath;
		    
		    File file = new File(yemi);
		    if(file.exists()){
		    	file.delete();
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }else{
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }
		    
		    this.setFileName("Metrics-report.xls");
		    fileInputStream = new FileInputStream(new File(automationMetricsToolPath));
		    
		    response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
			
			if(fileInputStream != null){
				OutputStream outputStream = response.getOutputStream();
				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
		        // write bytes read from the input stream into the output stream
		        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
		        	outputStream.write(buffer, 0, bytesRead);
		        }
		 
		        outputStream.close();
			}
		
		return;
	}
	
	/**
	 * 
	 * @param clientDivisionReport
	 * @param response
	 * @throws IOException
	 */
	private void processDivisionAllClientReport(List<ClientDivisionReport> clientDivisionReport, HttpServletResponse response) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("Automation-Metrics");
		 HSSFRow rowhead = sheet.createRow((short) 0);
		 rowhead.createCell((short) 0).setCellValue("Division");
		 rowhead.createCell((short) 1).setCellValue("Client");
		 rowhead.createCell((int) 2).setCellValue("Production Page Count");
		 rowhead.createCell((short) 3).setCellValue("Actual Manual Metrics");
		 rowhead.createCell((short) 4).setCellValue("Actual Automation Metrics");
		 rowhead.createCell((short) 5).setCellValue("Automation Saved Metrics");
		 
		 int i = 1;
		 int rowCount = 0;
		 int manualMetricsHours = 0;
		 int manualMetricsMins = 0;
		 int automationMetricsHours = 0;
		 int automationMetricsMins = 0;
		 int savedAutomationMetricHrs = 0;
		 int savedAutomationMetricMins = 0;
		 for(ClientDivisionReport rs : clientDivisionReport){
			//System.out.println("=======" +rs.getAutomationToolName());
			HSSFRow row = sheet.createRow((short) i);
	        row.createCell((short) 0).setCellValue(rs.getDivisionName());
	        row.createCell((short) 1).setCellValue(rs.getClientName());
	        row.createCell((int) 2).setCellValue(rs.getNoOfPages());
	        /*row.createCell((short) 3).setCellValue(rs.getProjectedManuamMetrics());
	        row.createCell((int) 4).setCellValue(rs.getProjectedAutomationMetrics());*/
	        
	        
	        Integer mmHours = 0;
			Integer mmMinutes = 0;
			String mm = null;
			int mmins = 0;
			int amins = 0;
			 //Saved hours
			int savedAutomationHours = 0;
			int savedAutomationMins = 0;
			if(rs.getProjectedManuamMetrics() > 60){
				mmHours = (int) (rs.getProjectedManuamMetrics() / 60);
				mmMinutes = (int) (rs.getProjectedManuamMetrics()  % 60);
				mm = mmHours +" hr "+ mmMinutes + " mins"; 
				row.createCell((short) 3).setCellValue(mm);
			}else{
				mmins = (int) Math.round(rs.getProjectedManuamMetrics());
				mm = String.format("%.0f",rs.getProjectedManuamMetrics()) + " mins";
				row.createCell((short) 3).setCellValue(mm);
			}
			
			manualMetricsHours =  manualMetricsHours + mmHours;
			manualMetricsMins = manualMetricsMins + mmMinutes + mmins;
			
			Integer amHours = 0;
			Integer amMinutes = 0;
			String am = null;
			if(rs.getProjectedAutomationMetrics() > 60){
				amHours = (int) (rs.getProjectedAutomationMetrics() / 60);
				amMinutes = (int) (rs.getProjectedAutomationMetrics() % 60);
				am = amHours +" hr "+ amMinutes + " mins"; 
				row.createCell((short) 4).setCellValue(am);
			}else{
				amins = (int) Math.round(rs.getProjectedAutomationMetrics());
				am = String.format("%.0f",rs.getProjectedAutomationMetrics()) + " mins";
				row.createCell((short) 4).setCellValue(am);
			}
	        
			automationMetricsHours = automationMetricsHours + amHours;
			automationMetricsMins = automationMetricsMins + amMinutes + amins;
			
			if(mmHours > amHours){
				savedAutomationHours = mmHours-amHours;
			}else{
				savedAutomationHours = mmHours-amHours;
			}
			
			
			if(mmMinutes > amMinutes){
				savedAutomationMins = mmMinutes - amMinutes;
			}else{
				savedAutomationMins = amMinutes - mmMinutes;
			}
			
			savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationHours;
			savedAutomationMetricMins = savedAutomationMetricMins + savedAutomationMins;
			
			if(savedAutomationHours != 0){
				if(savedAutomationMins != 0){
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
				}else{
					System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" +savedAutomationHours+" hrs ");
					row.createCell((short) 5).setCellValue(savedAutomationHours+" hrs ");
				}
				
			}else{
				System.out.println("==="+rs.getDivisionName()+"-"+rs.getClientName()+"===" + savedAutomationMins +" mins");
				row.createCell((short) 5).setCellValue(savedAutomationMins+" mins ");
			}
			
	        i++;
		 }
		 
		 //System.out.println("Consolidated MM Hrs :: " +manualMetricsHours);
		 //System.out.println("Consolidated MM Mins conv hr :: " +manualMetricsMins/60);
		 //System.out.println("Consolidated MM Mins :: " +manualMetricsMins%60);
		 String projectedMMHrs = String.valueOf(manualMetricsHours + manualMetricsMins/60);
		 String projectedMMMins = String.valueOf(manualMetricsMins % 60);
		 
		 String projectedAMHrs = String.valueOf(automationMetricsHours + automationMetricsMins/60);
		 String projectedAMMins = String.valueOf(automationMetricsMins % 60);
		 
		 savedAutomationMetricHrs = savedAutomationMetricHrs + savedAutomationMetricMins/60;
		 savedAutomationMetricMins = savedAutomationMetricMins % 60;
		 String savedHrs = String.valueOf(savedAutomationMetricHrs +" hrs " +savedAutomationMetricMins+ " mins");
		 
		 Row pageTotal = sheet.createRow(i + 1);
		 Cell pageTotalText = pageTotal.createCell(1);
		 pageTotalText.setCellValue("Total ::");
	     
	     //System.out.println("==="+pageTotal.getRowNum());
	     
	     Cell totalPagesCell = pageTotal.createCell(2);
	     totalPagesCell.setCellFormula("SUM(C2:C" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     
	     Cell manualMetricsCell = pageTotal.createCell(3);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     manualMetricsCell.setCellValue(projectedMMHrs+" hrs " +projectedMMMins + " mins");
	     
	     Cell automationMetricsCell = pageTotal.createCell(4);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     automationMetricsCell.setCellValue(projectedAMHrs+" hrs " +projectedAMMins + " mins");
		 
	     Cell savedMetricsCell = pageTotal.createCell(5);
	     //manualMetricsCell.setCellFormula("SUM(F2:F" + String.valueOf(pageTotal.getRowNum()-1) + ")");
	     savedMetricsCell.setCellValue(savedHrs);
	     
		 String yemi = automationMetricsToolPath;
		    
		    File file = new File(yemi);
		    if(file.exists()){
		    	file.delete();
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }else{
		    	FileOutputStream fileOut = new FileOutputStream(file);
			    workbook.write(fileOut);
			    fileOut.close();
		    }
		    
		    this.setFileName("Metrics-report.xls");
		    fileInputStream = new FileInputStream(new File(automationMetricsToolPath));
		    
		    response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
			
			if(fileInputStream != null){
				OutputStream outputStream = response.getOutputStream();
				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
		        // write bytes read from the input stream into the output stream
		        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
		        	outputStream.write(buffer, 0, bytesRead);
		        }
		 
		        outputStream.close();
			}
		
		return;
	}
	
	public InputStream fileInputStream(){
		return fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
