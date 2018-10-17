package com.cm.cs.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.cm.cs.api.dao.ClientDao;
import com.cm.cs.api.model.AutomationInputs;
import com.cm.cs.api.model.AutomationJobs;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientForm;
import com.cm.cs.api.model.Division;
import com.cm.cs.api.model.ToolMetrics;
import com.cm.cs.api.service.IAutomationMetricsService;
import com.cm.cs.api.service.IAutomationSerivce;
import com.cm.cs.api.service.IClientService;
import com.cm.cs.api.service.IDivisionService;

/**
 * 
 * @author DHIVAKART
 *
 */
@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
	
	
	@Autowired
	IDivisionService divisionService;
	
	@Autowired
	IClientService clientService;
	
	@Autowired
	IAutomationSerivce automationService;
	
	@Autowired
	ClientDao clientDao;
	
	@Autowired
	IAutomationMetricsService automationMetricsService;
	
	@Value("${metrics.report.path}")
	private String automationMetricsToolPath;
	
	@Value("${job.report.path}")
	private String jobReportPath;
	
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
	 * Render automation inputs 
	 * page
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/getAutomationInput")
	public ModelAndView getAutomationInputs(ModelAndView model){
		return new ModelAndView("add-automation", "automationInputs", new AutomationInputs());
	}
	
	/**
	 * Render add client page
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/getClient")
	public ModelAndView getClients(ModelAndView model){
		return new ModelAndView("add-client", "clientForm", new ClientForm());
	}
	
	/**
	 * Add automation inputs
	 * @param automation
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/addAutomation")
	public ModelAndView addAutomation(@ModelAttribute AutomationInputs automation, BindingResult result, Model model){
		log.info("Adding the automation details {} " +automation.getAutomationName());
		
		AutomationTools automationDetails = null;
		try {
			if(result.hasErrors()){
				return new ModelAndView("add-automation");
			}
			
			if(automation != null){
				automationDetails = automationService.findByAutomationInputs(automation.getAutomationName(), automation.getDivision(), automation.getManualMetrics(), automation.getManualPages(), automation.getAutomationMetrics(), automation.getAutomationPages(), automation.getClient(), automation.getToolDescription());
				if(automationDetails != null){
					model.addAttribute("responseMsg", "Automation tool added successfully.");
				}else{
					model.addAttribute("responseMsg", "Automation tool not added.");
				}
			}
		} catch (Exception e) {
			log.error("Error while adding the automation details {} " +e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("add-automation");
	}
	
	@PostMapping(value = "/addClient")
	public ModelAndView addClient(@ModelAttribute ClientForm client, BindingResult result, Model model){
		log.info("Adding the client {} " +client.getClientName());
		
		Client clientDetails = null;
		try {
			if(result.hasErrors()){
				return new ModelAndView("add-client");
			}
			
			if(client != null){
				/*if(client.getDivisionName() != null){*/
					clientDetails = clientService.findByClientName(client.getClientName());
					if(clientDetails != null){
						model.addAttribute("responseMsg", "Client already exists.");
					}else{
						Date utilDate = new Date();
						Timestamp date = new Timestamp(utilDate.getTime());
						/*Division division = divisionService.findByDivision(Long.valueOf(client.getDivisionName()));
						Set<Division> divisionDetails = new HashSet<Division>();
						divisionDetails.add(division);*/
						
						Client clientObj = new Client();
						clientObj.setClientName(client.getClientName());
						/*clientObj.setClientDivisionMapping(divisionDetails);*/
						clientObj.setActive(true);
						clientObj.setCreatedOn(date);
						clientObj.setDeleted(false);
						clientObj = clientDao.save(clientObj);
						if(clientObj != null){
							model.addAttribute("responseMsg", "Client saved successfully.");
						}
					}
				}/*else{
					model.addAttribute("responseMsg", "Please choose the division.");
				}*/
			/*}*/
		} catch (Exception e) {
			log.error("Error while adding the clients {} " +e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("add-client");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/getToolMetrics")
	public void getAutomationMetrics(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Getting the automation metrics details...");
		String jsonOutput = "";
		//Integer hour = 60;
		try {
			List<ToolMetrics> metricsList = automationMetricsService.getAutomationMetrics();
			if(metricsList.size() > 0){
				for(ToolMetrics automationMetrics : metricsList){
					if(!jsonOutput.equalsIgnoreCase(""))
						jsonOutput += ",[";
					else
						jsonOutput += "[";
						jsonOutput += "\"<a href='' onclick='javascript:popup(" + automationMetrics.getAutomationToolId() + ");'>" + automationMetrics.getAutomationName()
					+ "</a>\"";
						jsonOutput += ",\"" + automationMetrics.getClientName() + "\"";
						jsonOutput += ",\"" + automationMetrics.getDivisionName() + "\"";
						jsonOutput += ",\"" + automationMetrics.getManualPagesCount() + "\"";
						jsonOutput += ",\"" + automationMetrics.getProdNoOfPages() + "\"";
						
						//Double projectedManualMetrics = automationMetrics.getProjectedManualMetrics();
						Integer mmHours = 0;
						Integer mmMinutes = 0;
						Integer amHours = 0;
						Integer amMinutes = 0;
						if(automationMetrics.getProjectedManualMetrics() > 60){
							mmHours = (int) (automationMetrics.getProjectedManualMetrics() / 60);
							mmMinutes = (int) (automationMetrics.getProjectedManualMetrics()  % 60);
							jsonOutput += ",\"" + mmHours + " hr "+  mmMinutes + " mins"+ "\"";
						}else{
							mmMinutes = (int)Math.round(automationMetrics.getProjectedManualMetrics());
							jsonOutput += ",\"" + String.format("%.0f",automationMetrics.getProjectedManualMetrics()) + " mins"+"\"";
						}
						//jsonOutput += ",\"" + automationMetrics.getProjectedManualMetrics() + "\"";
						
						
						if(automationMetrics.getProjectedAutomationMetrics() > 60){
							amHours = (int) (automationMetrics.getProjectedAutomationMetrics() / 60);
							amMinutes = (int) (automationMetrics.getProjectedAutomationMetrics() % 60);
							jsonOutput += ",\"" + amHours + " hr "+ amMinutes + " mins"+ "\"";
						}else{
							amMinutes = (int)Math.round(automationMetrics.getProjectedAutomationMetrics());
							jsonOutput += ",\"" + String.format("%.0f",automationMetrics.getProjectedAutomationMetrics()) + " mins"+"\"";
						}
						
						
						//Saved hours
						//2:45 0:55
						int savedAutomationHours = 0;
						int savedAutomationMins = 0;
						if(amHours != 0){
							if(mmHours > amHours){
								savedAutomationHours = mmHours-amHours;
							}else{
								savedAutomationHours = mmHours-amHours;
							}
						}else{
							savedAutomationHours = mmHours;
							savedAutomationMins = mmMinutes + amMinutes;
							
							int hrs= 0;
							if(savedAutomationMins > 60){
								hrs = savedAutomationMins/60;
								savedAutomationMins = savedAutomationMins % 60;
								savedAutomationHours = savedAutomationHours - hrs;
							}else{
								savedAutomationHours = mmHours;
								if(mmMinutes > amMinutes){
									savedAutomationMins = mmMinutes - amMinutes;
								}else{
									savedAutomationMins = amMinutes - mmMinutes;
								}
							}
						}
						
						
						if(amHours !=0){
						
							if(mmMinutes > amMinutes){
								savedAutomationMins = mmMinutes - amMinutes;
							}else{
								savedAutomationMins = amMinutes - mmMinutes;
							}
						}
						
						if(savedAutomationHours != 0){
							if(savedAutomationMins != 0){
								System.out.println("==="+automationMetrics.getAutomationName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
								jsonOutput += ",\"" + savedAutomationHours + " hrs "+ savedAutomationMins + " mins"+ "\"";
							}else{
								System.out.println("==="+automationMetrics.getAutomationName()+"===" +savedAutomationHours+" hrs ");
								jsonOutput += ",\"" + savedAutomationHours + " hrs "+"\"";
							}
							
						}else{
							System.out.println("==="+automationMetrics.getAutomationName()+"===" + savedAutomationMins +" mins");
							jsonOutput += ",\"" + savedAutomationMins + " mins "+"\"";
						}
						
						
						//jsonOutput += ",\"" + automationMetrics.getProjectedAutomationMetrics() + "\"";
						jsonOutput += "]";
						
				}
			}
		} catch (Exception e) {
			log.error("Error while getting the automation metrics details {} " +e.getMessage());
			e.printStackTrace();
		}
		
		response.getWriter().write("{\"data\":[" + jsonOutput + "]}");
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/automationJobDetails")
	public ModelAndView automationJobDetails(HttpServletRequest request, Model model){
		ModelAndView mav = new ModelAndView("automation-job-details");
		try {
			Integer automationId = (Integer.parseInt(request.getParameter("automationId")));
			model.addAttribute("automationId", automationId);
		} catch (Exception e) {
			log.error("Error while fetching the automation details for tool :: " + request.getParameter("automationId")
					+ e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/getAutomationJobs")
	public void automationJobDetails(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Fetching the automation job details {} " +request.getParameter("automationId"));
		String jsonOutput = "";
		try {
			List<AutomationJobs> automationJobList = automationMetricsService.automationJobList(Long.valueOf(request.getParameter("automationId")));
			if(automationJobList.size() > 0){
				for(AutomationJobs automationJobs : automationJobList){
					if(!jsonOutput.equalsIgnoreCase(""))
						jsonOutput += ",[";
					else
						jsonOutput += "[";
						jsonOutput += "\"" + automationJobs.getJobId() + "\"";
						jsonOutput += ",\"" + automationJobs.getJobType() + "\"";
						if(automationJobs.getJobCreatedOn() != null){
							//String jobAutomationDate = sdf.format(automationJobs.getJobCreatedOn());
							jsonOutput += ",\"" + automationJobs.getJobCreatedOn() + "\"";
						}else{
							jsonOutput += ",\"- \"";
						}
						
						jsonOutput += ",\"" + automationJobs.getPageOrImageCount() + "\"";
						jsonOutput += "]";
				}
			}
		} catch (Exception e) {
			log.error("Error while fetching the automation job details {} " +e.getMessage());
			e.printStackTrace();
		}
		response.getWriter().write("{\"data\":[" + jsonOutput + "]}");
	}
	
	@GetMapping(value = "/getAutomationReport")
	public void automationMetricsReport(final HttpServletResponse response) throws IOException{
		log.info("Reading the style profile data.");
		//JasperPrint jp = null;
		try {
			List<ToolMetrics> list = automationMetricsService.getAutomationMetrics();
			/*StyleProfileReport report = new StyleProfileReport(list);
			jp = report.getConversionReport();
			
			reportService.writeXlsxReport(jp, response, "styleProfileReport");*/
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet sheet = workbook.createSheet("Tool-Metrics");
			 HSSFRow rowhead = sheet.createRow((short) 0);
			 rowhead.createCell((short) 0).setCellValue("Automation Name");
			 rowhead.createCell((short) 1).setCellValue("Client");
			 rowhead.createCell((short) 2).setCellValue("Division");
			 rowhead.createCell((int) 3).setCellValue("Total Pages");
			 rowhead.createCell((int) 4).setCellValue("Production Page Count");
			 rowhead.createCell((short) 5).setCellValue("Manual Metrics");
			 rowhead.createCell((short) 6).setCellValue("Automation Metrics");
			 rowhead.createCell((short) 7).setCellValue("Saved Metrics");
			 rowhead.createCell((short) 8).setCellValue("Description");
			 
			 int i = 1;
			    for(ToolMetrics rs : list){
			        HSSFRow row = sheet.createRow((short) i);
			        row.createCell((short) 0).setCellValue(rs.getAutomationName());
			        row.createCell((short) 1).setCellValue(rs.getClientName());
			        row.createCell((short) 2).setCellValue(rs.getDivisionName());
			        row.createCell((int) 3).setCellValue(rs.getManualPagesCount());
			        row.createCell((int) 4).setCellValue(rs.getProdNoOfPages());
			        
			        Integer mmHours = 0;
					Integer mmMinutes = 0;
					String mm = null;
					if(rs.getProjectedManualMetrics() > 60){
						mmHours = (int) (rs.getProjectedManualMetrics() / 60);
						mmMinutes = (int) (rs.getProjectedManualMetrics()  % 60);
						mm = mmHours +" hr "+ mmMinutes + " mins"; 
						row.createCell((short) 5).setCellValue(mm);
					}else{
						mm = String.format("%.0f",rs.getProjectedManualMetrics()) + " mins";
						row.createCell((short) 5).setCellValue(mm);
					}
					
					Integer amHours = 0;
					Integer amMinutes = 0;
					String am = null;
					if(rs.getProjectedAutomationMetrics() > 60){
						amHours = (int) (rs.getProjectedAutomationMetrics() / 60);
						amMinutes = (int) (rs.getProjectedAutomationMetrics() % 60);
						am = amHours +" hr "+ amMinutes + " mins"; 
						row.createCell((short) 6).setCellValue(am);
					}else{
						am = String.format("%.0f",rs.getProjectedAutomationMetrics()) + " mins";
						row.createCell((short) 6).setCellValue(am);
					}
			        
					
					//Saved hours
					int savedAutomationHours = 0;
					int savedAutomationMins = 0;
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
					
					if(savedAutomationHours != 0){
						if(savedAutomationMins != 0){
							//System.out.println("==="+rs.getAutomationName()+"===" +savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
							row.createCell((short) 7).setCellValue(savedAutomationHours+" hrs "+ savedAutomationMins +" mins");
						}else{
							//System.out.println("==="+rs.getAutomationName()+"===" +savedAutomationHours+" hrs ");
							row.createCell((short) 7).setCellValue(savedAutomationHours+" hrs ");
						}
						
					}else{
						//System.out.println("==="+rs.getAutomationName()+"===" + savedAutomationMins +" mins");
						row.createCell((short) 7).setCellValue(savedAutomationMins+" mins ");
					}
					
					if(rs.getDescription() != null){
						row.createCell((short) 8).setCellValue(rs.getDescription());
					}else{
						row.createCell((short) 8).setCellValue("");
					}
					
			        i++;
			    }
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
			    
			    this.setFileName("Automation-Metrics-report.xls");
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
		} catch (Exception e) {
			
		} finally {
			fileInputStream.close();
		}
		
	}
	
	/**
	 * 
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/getJobReport")
	public void automationJobReport(HttpServletRequest request,final HttpServletResponse response) throws IOException{
		log.info("Reading the style profile data.");
		//JasperPrint jp = null;
		try {
			List<AutomationJobs> automationJobList = automationMetricsService.automationJobReport();
			/*StyleProfileReport report = new StyleProfileReport(list);
			jp = report.getConversionReport();
			
			reportService.writeXlsxReport(jp, response, "styleProfileReport");*/
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet sheet = workbook.createSheet("Job-Details");
			 HSSFRow rowhead = sheet.createRow((short) 0);
			 rowhead.createCell((short) 0).setCellValue("Automation Name");
			 rowhead.createCell((short) 1).setCellValue("Job ID");
			 rowhead.createCell((short) 2).setCellValue("Job Type");
			 rowhead.createCell((int) 3).setCellValue("Page/Image Count");
			 rowhead.createCell((short) 4).setCellValue("Job Created On");
			 
			 int i = 1;
			    for(AutomationJobs rs : automationJobList){
			        HSSFRow row = sheet.createRow((short) i);
			        row.createCell((short) 0).setCellValue(rs.getAutomationName());
			        row.createCell((short) 1).setCellValue(rs.getJobId());
			        row.createCell((short) 2).setCellValue(rs.getJobType());
			        row.createCell((int) 3).setCellValue(rs.getPageOrImageCount());
			        row.createCell((short) 4).setCellValue(rs.getJobCreatedOn());
			        i++;
			    }
			    String yemi = jobReportPath;
			    
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
			    
			    this.setFileName("Job-report.xls");
			    fileInputStream = new FileInputStream(new File(jobReportPath));
			    
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
		} catch (Exception e) {
			
		} finally {
			fileInputStream.close();
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/getAutomationTools")
	public ModelAndView getAutomationTools(){
		return new ModelAndView("automation-details");
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/getAutomationDetails")
	public void automationDetails(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Getting the automation details {} ");
		String jsonOutput = "";
		try {
			List<AutomationTools> automationDetails = automationService.automationDetailsList();
			for(AutomationTools automation : automationDetails){
				if(!jsonOutput.equalsIgnoreCase(""))
					jsonOutput += ",[";
				else
					jsonOutput += "[";
					jsonOutput += "\"" + automation.getAutomationName() + "\"";
					jsonOutput += ",\"" + automation.getDivision().getDivisionName() + "\"";
					
					jsonOutput += ",\"" + automation.getClient().getClientName() + "\"";
					jsonOutput += ",\"" + automation.getManualMetrics() + "\"";
					jsonOutput += ",\"" + automation.getManualNoOfPages() + "\"";
					jsonOutput += ",\"" + automation.getAutomationMetrics() + "\"";
					jsonOutput += ",\"" + automation.getAutomationNoOfPages() + "\"";
					jsonOutput += ",\"" + automation.getCreatedOn() + "\"";
					if(automation.getModifiedOn() != null){
						jsonOutput += ",\"" + automation.getModifiedOn() + "\"";
					}else{
						jsonOutput += ",\"- \"";
					}
					/*jsonOutput += ",\"<a href='editAutomation?id="+automation.getId()+"'>Edit</a>\"";*/
					jsonOutput += "]";
			}
		} catch (Exception e) {
			log.error("Error while getting the automation details {} " +e.getMessage());
			e.printStackTrace();
		}
		response.getWriter().write("{\"data\":[" + jsonOutput + "]}");
	}
	
	@GetMapping(value = "/editAutomation")
	public ModelAndView editAutomation(HttpServletRequest request){
		Long automationId = Long.valueOf(request.getParameter("id"));
		AutomationTools automationInputs = automationService.findByAutomationId(automationId);
		ModelAndView model = new ModelAndView("add-automation");
		model.addObject("automationInputs", automationInputs);
		return model;
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/downloadAutomation")
	public void automationDetailsReport(HttpServletRequest request,final HttpServletResponse response) throws IOException{
		log.info("Reading the style profile data.");
		//JasperPrint jp = null;
		try {
			List<AutomationTools> automationDetails = automationService.automationDetailsList();
			/*StyleProfileReport report = new StyleProfileReport(list);
			jp = report.getConversionReport();
			
			reportService.writeXlsxReport(jp, response, "styleProfileReport");*/
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet sheet = workbook.createSheet("Automation-Details");
			 HSSFRow rowhead = sheet.createRow((short) 0);
			 rowhead.createCell((short) 0).setCellValue("Automation Name");
			 rowhead.createCell((short) 1).setCellValue("Division");
			 rowhead.createCell((short) 2).setCellValue("Client");
			 rowhead.createCell((int) 3).setCellValue("Manual Metrics");
			 rowhead.createCell((short) 4).setCellValue("Manual Pages");
			 rowhead.createCell((short) 5).setCellValue("Automation Metrics");
			 rowhead.createCell((short) 6).setCellValue("Automation Pages");
			/* rowhead.createCell((short) 7).setCellValue("Created On");*/
			 rowhead.createCell((short) 8).setCellValue("Description");
			 
			 int i = 1;
			    for(AutomationTools rs : automationDetails){
			        HSSFRow row = sheet.createRow((short) i);
			        row.createCell((short) 0).setCellValue(rs.getAutomationName());
			        row.createCell((short) 1).setCellValue(rs.getDivision().getDivisionName());
			        row.createCell((short) 2).setCellValue(rs.getClient().getClientName());
			        row.createCell((int) 3).setCellValue(rs.getManualMetrics());
			        row.createCell((short) 4).setCellValue(rs.getManualNoOfPages());
			        row.createCell((short) 5).setCellValue(rs.getAutomationMetrics());
			        row.createCell((short) 6).setCellValue(rs.getAutomationNoOfPages());
			       /* row.createCell((short) 7).setCellValue(rs.getCreatedOn());*/
			        row.createCell((short) 8).setCellValue(rs.getToolDescription());
			        i++;
			    }
			    String yemi = jobReportPath;
			    
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
			    
			    this.setFileName("Automation-Details.xls");
			    fileInputStream = new FileInputStream(new File(jobReportPath));
			    
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
		} catch (Exception e) {
			
		} finally {
			fileInputStream.close();
		}
		
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
