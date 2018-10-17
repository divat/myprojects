package com.cm.style.profile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.StyleDetails;
import com.cm.style.profile.model.StyleProfileDetails;
import com.cm.style.profile.service.IClientService;
import com.cm.style.profile.service.IPublicationService;
import com.cm.style.profile.service.IPublisherService;
import com.cm.style.profile.service.ReportService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author DHIVAKART
 *
 */

@ApiIgnore
@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IPublisherService publisherService;
	
	@Autowired
	private IPublicationService publicationService;
	
	@Autowired
	private IClientService clientService;
	
	@Autowired
	ReportService reportService;
	
	@Value("${report.path}")
	private String styleProfileReportPath;
	
	private InputStream fileInputStream;

	private String fileName;
	
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Get the style details
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/styleDetails")
	public void getStyleDetails(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Getting the style details....");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String jsonOutput = "";
		try {
			List<StyleDetails> list = publisherService.styleDetails(authentication.getName());
			if(list.size() > 0){
				for(StyleDetails s : list){
					if(!jsonOutput.equalsIgnoreCase(""))
						jsonOutput += ",[";
					else
						jsonOutput += "[";
						jsonOutput += "\"" + s.getClientName() + "\"";
						jsonOutput += ",\"" + s.getPublisherName() + "\"";
						jsonOutput += ",\"" + s.getPublicatioName() + "\"";
						/*jsonOutput += ",\"" + s.getStyleUID() + "\"";*/
						jsonOutput += ",\"" + s.getCreatedOn() + "\"";
						jsonOutput += ",\"" + s.getModifiedOn() + "\"";
						jsonOutput += "]";
				}
			}
		} catch (Exception e) {
			log.error("Error while getting the style details :: " +e.getMessage());
			e.printStackTrace();
		}
		response.getWriter().write("{\"data\":[" + jsonOutput + "]}");
	}
	
	@GetMapping(value = "/getPublisher")
	public ModelAndView getChpater(ModelAndView model){
		return new ModelAndView("add-publisher", "publisher", new StyleProfileDetails());
	}
	
	@PostMapping(value = "/addPublisher")
	public ModelAndView addPublisher(@ModelAttribute StyleProfileDetails publisher, BindingResult result, Model model){
		log.info("Adding the publisher/publication details.");
		
		Client client = null;
		Publisher publishers = null;
		Publication publication = null;
		try {
			if(result.hasErrors()){
				return new ModelAndView("add-publisher");
			}
			
			if(publisher != null){
				
				client = clientService.findByClient(publisher.getClientName());
				if(client != null){
					//model.addAttribute("responseMsg", "Client already exists.");
				}else{
					client = clientService.findByClientName(publisher.getClientName());
				}
				
				
				publishers = publisherService.findByPublisherName(publisher.getPublisherName());
				if(publishers != null){
					publication = publicationService.findByPublicationName(publisher.getPublicationName(), publishers);
					if(publication == null){
						//Publisher exists but publication not exists.
						Publication publicationName = publicationService.addPublication(publisher.getPublicationName(), publishers);
						if(publicationName != null){
							model.addAttribute("responseMsg", "Publication details added.");
						}else{
							model.addAttribute("responseMsg", "Error while adding publication.");
						}
					}else{
						model.addAttribute("responseMsg", "Publisher/Publication already exists.");
					}
				}else{
					//Publisher not exist
					publishers = publisherService.checkPublisherExists(publisher.getPublisherName());
					if(publishers != null){
						Publication publicationName = publicationService.addPublication(publisher.getPublicationName(), publishers);
						if(publicationName != null){
							model.addAttribute("responseMsg", "Publisher/Publication details added.");
						}else{
							model.addAttribute("responseMsg", "Error while adding publication.");
						}
					}else{
						model.addAttribute("responseMsg", "Error while adding publisher.");
					}
				}
				
				
						
			}
		} catch (Exception e) {
			log.error("Error while adding the publisher/publication details." +e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("add-publisher");
	}
	
	@PostMapping(value = "/getStyleProfileData")
	public void styleProfileReport(final HttpServletResponse response) throws IOException{
		log.info("Reading the style profile data.");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//JasperPrint jp = null;
		try {
			List<StyleDetails> list = reportService.styleProfileData(authentication.getName());
			/*StyleProfileReport report = new StyleProfileReport(list);
			jp = report.getConversionReport();
			
			reportService.writeXlsxReport(jp, response, "styleProfileReport");*/
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet sheet = workbook.createSheet("style-profile");
			 HSSFRow rowhead = sheet.createRow((short) 0);
			 rowhead.createCell((short) 0).setCellValue("Client Name");
			 rowhead.createCell((short) 1).setCellValue("Publisher Name");
			 rowhead.createCell((short) 2).setCellValue("Publication Name");
			 rowhead.createCell((short) 3).setCellValue("Created On");
			 rowhead.createCell((short) 4).setCellValue("Modified On");
			 
			 int i = 1;
			    for(StyleDetails rs : list){
			        HSSFRow row = sheet.createRow((short) i);
			        row.createCell((short) 0).setCellValue(rs.getClientName());
			        row.createCell((short) 1).setCellValue(rs.getPublisherName());
			        row.createCell((short) 2).setCellValue(rs.getPublicatioName());
			        row.createCell((short) 3).setCellValue(rs.getCreatedOn());
			        row.createCell((short) 4).setCellValue(rs.getCreatedOn());
			        i++;
			    }
			    String yemi = styleProfileReportPath;
			    
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
			    
			    this.setFileName("Style-profile-report.xls");
			    fileInputStream = new FileInputStream(new File(styleProfileReportPath));
			    
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
