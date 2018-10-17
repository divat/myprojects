package com.codemantra.maestro.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.codemantra.maestro.model.FileMetadata;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.service.MasterDataService;

/**
 * Read job from excel
 * @author DHIVAKART
 *
 */
@Component
public class ReadJobData {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadJobData.class);
	
	@Autowired
	MasterDataService masterDataService;
	
	/**
	 * Read job list for validation
	 * @param path
	 * @param job
	 * @return
	 */
	public List<MasterData> validationJobList(String path, FileMetadata job){
		LOGGER.info("Checking for duplicate jobs :: " +job.getId()+" "+job.getFileName());
		List<MasterData> list = new ArrayList<MasterData>();
		try {
			Workbook workbook = WorkbookFactory.create(new File(path));
	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rowIterator = sheet.iterator();
	        rowIterator.next();
	        while(rowIterator.hasNext()){
	        	MasterData masterData = new MasterData();
	            Row row = rowIterator.next();
	            
	            Cell serialNo = row.getCell(0, Row.RETURN_NULL_AND_BLANK);
	            if (serialNo == null){
	            	masterData.setsId(null);
	            }else if("".equals(serialNo.getCellType() == Cell.CELL_TYPE_NUMERIC)){
	            	masterData.setsId(null);
	            }else{ /*if(serialNo.getCellType() == Cell.CELL_TYPE_NUMERIC)*/
	            	serialNo.setCellType(Cell.CELL_TYPE_STRING);
	            	masterData.setsId(serialNo.getStringCellValue().trim());
	            }
	            
	           /* if (serialNo == null)
	            	masterData.setsId(null);
	            else if("".equals(serialNo.getStringCellValue().trim()))
	            	masterData.setsId(null);
	            else 
	            	masterData.setsId(row.getCell(0).getStringCellValue());*/
	            
	            Cell templateType = row.getCell(1, Row.RETURN_NULL_AND_BLANK);
	            if (templateType == null)
	            	masterData.setTemplateType(null);
	            else if("".equals(templateType.getStringCellValue().trim()))
	            	masterData.setTemplateType(null);
	            else 
	            	masterData.setTemplateType(row.getCell(1).getStringCellValue().trim());
	            
	            
	            Cell clientId = row.getCell(2, Row.RETURN_NULL_AND_BLANK);
	            if (clientId == null)
	            	masterData.setClientId(null);
	            else if("".equals(clientId.getStringCellValue().trim()))
	            	masterData.setClientId(null);
	            else 
	            	masterData.setClientId(row.getCell(2).getStringCellValue().trim());
	            
	            Cell jobId = row.getCell(3, Row.RETURN_NULL_AND_BLANK);
	            if (jobId == null){
	            	masterData.setJobId(null);
	            }else if("".equals(jobId.getStringCellValue().trim())){
	            	masterData.setJobId(null);
	            }else if(row.getCell(2).getStringCellValue().equalsIgnoreCase("tf_encyclopedia")){
	            	if(row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
	            		row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
	            		masterData.setJobId(row.getCell(3).getStringCellValue().trim());
	            	}
	            }else{
	            	masterData.setJobId(row.getCell(3).getStringCellValue().trim());
	            }
	            
	            Cell templateName = row.getCell(4, Row.RETURN_NULL_AND_BLANK);
	            if (templateName == null)
	            	masterData.setStage4TemplateName(null);
	            else if("".equals(templateName.getStringCellValue().trim()))
	            	masterData.setStage4TemplateName(null);
	            else 
	            	masterData.setStage4TemplateName(row.getCell(4).getStringCellValue().trim());
	            
	            Double noOfManuscripts = row.getCell(5).getNumericCellValue();
	            masterData.setNoOfManuscripts(noOfManuscripts.intValue());
	            
	            list.add(masterData);
	        }
		} catch (Exception e) {
			LOGGER.error("Error while checking duplicate jobs :: " +job.getId()+" "+job.getFileName());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * List of job details
	 * to persist into database
	 * @param path
	 * @param job
	 * @return
	 */
	public List<MasterData> masterJobList(String path, FileMetadata job){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		
		List<MasterData> masterDataList = new ArrayList<MasterData>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String jobId = "";
		
		try {
			// Creating a Workbook from an Excel file (.xls or .xlsx)
	        Workbook workbook = WorkbookFactory.create(new File(path));
	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rowIterator = sheet.iterator();
	        rowIterator.next();
	        
	        while(rowIterator.hasNext()){
	        	MasterData masterData = new MasterData();
	            Row row = rowIterator.next();
	            
	            Cell serialNo = row.getCell(0, Row.RETURN_NULL_AND_BLANK);
	            if (serialNo == null){
	            	masterData.setsId(null);
	            }else if("".equals(serialNo.getCellType() == Cell.CELL_TYPE_NUMERIC)){
	            	masterData.setsId(null);
	            }else{ 
	            	serialNo.setCellType(Cell.CELL_TYPE_STRING);
            		masterData.setsId(serialNo.getStringCellValue().trim());
	            }
	            
	            Cell templateType = row.getCell(1, Row.RETURN_NULL_AND_BLANK);
	            if (templateType == null){
	            	masterData.setTemplateType(null);
	            }else if("".equals(templateType.getStringCellValue().trim())){
	            	masterData.setTemplateType(null);
	            }else{ 
	            	masterData.setTemplateType(row.getCell(1).getStringCellValue().trim());
	            }
	            
	            Cell clientId = row.getCell(2, Row.RETURN_NULL_AND_BLANK);
	            if (clientId == null){
	            	masterData.setClientId(null);
	            }else if("".equals(clientId.getStringCellValue().trim())){
	            	masterData.setClientId(null);
	            }else{ 
	            	masterData.setClientId(row.getCell(2).getStringCellValue().trim());
	            }
	            
	            Cell jobName = row.getCell(3, Row.RETURN_NULL_AND_BLANK);
	            if (jobName == null){
	            	masterData.setJobId(null);
	            }else if("".equals(jobName.getStringCellValue().trim())){
	            	masterData.setJobId(null);
	            }else if(row.getCell(2).getStringCellValue().equalsIgnoreCase("tf_encyclopedia")){
	            	if(row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
	            		row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
	            		masterData.setJobId(row.getCell(3).getStringCellValue());
	            	}
	            }else{
	            	masterData.setJobId(row.getCell(3).getStringCellValue().trim());
	            }
	            
	            Cell templateName = row.getCell(4, Row.RETURN_NULL_AND_BLANK);
	            if (templateName == null)
	            	masterData.setStage4TemplateName(null);
	            else if("".equals(templateName.getStringCellValue().trim()))
	            	masterData.setStage4TemplateName(null);
	            else 
	            	masterData.setStage4TemplateName(row.getCell(4).getStringCellValue().trim());
	            
	            jobId = row.getCell(3).getStringCellValue().trim();
	            String[] isbn = jobId.split("_");
				String jobIsbn = isbn[0];
	            masterData.setJobIsbn(jobIsbn);
	            
	            Double noOfManuscripts = row.getCell(5).getNumericCellValue();
	            masterData.setNoOfManuscripts(noOfManuscripts.intValue());
	            masterData.setFileMetadata(job);
	            masterData.setJobStatus("IN-PROGRESS");
	            masterData.setCreatedOn(date);
	            masterData.setModifiedOn(date);
	            masterData.setCreatedBy(authentication.getName());
	            masterData.setNewJob(true);
	            masterDataList.add(masterData);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return masterDataList;
	}
	
	/**
	 * Validate job data
	 * @param list
	 * @return
	 */
	public List<String> validateJobs(List<MasterData> list) {
		List<String> errorList = new ArrayList<String>();
		List<String> clientIdList = new ArrayList<String>();
		List<String> jobIdList = new ArrayList<String>();
		List<String> isbnList = new ArrayList<String>();
		List<String> serialNoList = new ArrayList<String>();
		try {
			for (MasterData jobs : list) {
				if (jobs.getJobId() != null && jobs.getClientId() != null){
					clientIdList.add(jobs.getClientId());
					jobIdList.add(jobs.getJobId());
					String[] jobIsbn = jobs.getJobId().split("_");
					String isbn = jobIsbn[0];
					isbnList.add(isbn);
					serialNoList.add(jobs.getsId());
				}
			}
			
			//Author name number validation
			for(String jobId : jobIdList){
				String[] authorNameSplit = jobId.split("_");
				String authorName = authorNameSplit[1];
				boolean hasDigitInAuthor = authorName.matches(".*\\d+.*");
				if(hasDigitInAuthor){
					errorList.add("Job Id author name with number not allowed");
				}
			}
			
			List<MasterData> duplicateJobList = null;
			if(!clientIdList.isEmpty() && !jobIdList.isEmpty()){
				duplicateJobList = masterDataService.checkJobExists(clientIdList, jobIdList);
			}
			
			//Check duplicate jobs by job with author
			if (duplicateJobList != null && !duplicateJobList.isEmpty()) {
				errorList.add("Duplicate jobs found");
			}

			//Check duplicate jobs based on isbn
			if(!clientIdList.isEmpty() && !jobIdList.isEmpty()){
				if (duplicateJobList.isEmpty()) {
					List<MasterData> duplicateIsbnList = masterDataService.checkJobIsbnExists(isbnList);
					
					if (duplicateIsbnList != null && !duplicateIsbnList.isEmpty()) {
						errorList.add("Jobs with this ISBN already exists");
					}
				}
			}
			
			if(!serialNoList.isEmpty()){
				List<MasterData> duplicateSNoList = masterDataService.checkSerialNoExists(serialNoList);
				if(duplicateSNoList != null && !duplicateSNoList.isEmpty()){
					errorList.add("Serial no already exists");
				}
			}
			
			for (MasterData data : list) {
				
				String jobId = data.getJobId();
				String clientId = data.getClientId();
				String serialNo = data.getsId();
				String templateType = data.getTemplateType();
				String templateName = data.getStage4TemplateName();
				Integer noOfManuscripts = data.getNoOfManuscripts();
				
				if(templateType == null){
					errorList.add("Template type should not be empty.");
				}
				
				if(noOfManuscripts == null){
					errorList.add("Manuscripts should not be blank or 0.");
				}
				
				if(noOfManuscripts == 0){
					errorList.add("Manuscripts should not be blank or 0.");
				}
				
				if(noOfManuscripts != null){
					if(noOfManuscripts > 200){
						errorList.add("Manuscripts should not be greater 200.");
					}
				}
				
				//Client validation for template type TF_HSS
				templateType = templateType.trim();
				
				if((templateType.equalsIgnoreCase("TF_Mono") || templateType.equalsIgnoreCase("TF_PM") || templateType.equalsIgnoreCase("TF_SPIB") || templateType.equalsIgnoreCase("TF_MW") || templateType.equalsIgnoreCase("TF_LAW") || templateType.equalsIgnoreCase("TF_TS")) && !clientId.trim().equalsIgnoreCase("TF_HSS")){
					errorList.add("Client Id should be TF_HSS for template type");
				}
				
				/*//Template type validation for client TF_HSS
				if(clientId.trim().equalsIgnoreCase("TF_HSS") && templateType.equalsIgnoreCase("TF_Mono") || templateType.equalsIgnoreCase("TF_PM") || templateType.equalsIgnoreCase("TF_SPIB") || templateType.equalsIgnoreCase("TF_MW") || templateType.equalsIgnoreCase("TF_LAW") || templateType.equalsIgnoreCase("TF_TS")){
					//errorList.add("Template type mismatch for client TF_HSS");
				}else{
					errorList.add("Template type mismatch for client TF_HSS");
				}*/
				
				//Client and template type validation for TF_STEM
				if(templateType.trim().equalsIgnoreCase("TF_STEM") && !clientId.equalsIgnoreCase("TF_STEM")){
					errorList.add("Client Id should be TF_STEM for template type");
				}
				
				if(clientId.equalsIgnoreCase("TF_STEM") && !templateType.equalsIgnoreCase("TF_STEM")){
					errorList.add("Template type should be TF_STEM for client id");
				}
				
				//Client and template type validation for TF_ENCYCLOPEDIA
				if(templateType.equalsIgnoreCase("TF_ENCYCLOPEDIA") && !clientId.equalsIgnoreCase("TF_ENCYCLOPEDIA")){
					errorList.add("Client Id should be TF_ENCYCLOPEDIA for template type");
				}
				
				if(clientId.equalsIgnoreCase("TF_ENCYCLOPEDIA") && !templateType.equalsIgnoreCase("TF_ENCYCLOPEDIA")){
					errorList.add("Client Id should be TF_ENCYCLOPEDIA for template type");
				}
				
				if(noOfManuscripts == null){
					errorList.add("Manuscripts field should not be empty");
				}
				
				if(serialNo == null){
					errorList.add("Serial no should not be empty");
				}
				
				
				/*if(templateName == null){
					errorList.add("Template name should not be null");
				}*/
				
				if (jobId == null) {
					errorList.add("Job id should not be empty");
				}

				if (clientId == null || clientId == "") {
					errorList.add("Client id should not be empty");
				}
				
				if(clientId != null){
					if(!clientId.equalsIgnoreCase("tf_encyclopedia")){
						boolean isValidJob = validatejobId(jobId);
						if (isValidJob) {
							errorList.add("Job id should be 13 digit ISBN");
						}
					}else{
						boolean isValid9DigitISBN = validate9DigitjobId(jobId);
						if(isValid9DigitISBN){
							errorList.add("Job id should be 9 digit for encyclopedia.");
						}
					}
				}

				if(clientId.equals("TF_HSS")){
					if(serialNo.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$")){
						errorList.add("Client TF_HSS serial no should be digit");
					}
				}
				
				if(clientId.equals("TF_HSS")){
					
					if(!(serialNo.length() > 2 && serialNo.length() < 5)){
						errorList.add("Client TF_HSS serial no should be 3 or 4 digits");
					}
					
					/*if(!(serialNo.length() < 5)){
						errorList.add("Client TF_HSS serial no should be 3 or 4 digits");
					}*/
				}
				/*if (data.getStage1CopyEdit() == null || data.getStage2Graphics() == null || data.getStage6MaestroMappingPath() == null
						|| data.getStage7StandardGenericStyleSheet() == null) {
					errorList.add("Required path fields are empty");
				}*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorList;

	}
	
	/**
	 *Validate 13 digit ISBN jobId
	 * @param jobId
	 * @return
	 */
	public boolean validatejobId(String jobId){
		boolean isValidJob = false;
		String result = "";
		String regex = "\\d+";
		try {
			if(jobId != null){
				String[] jobName = jobId.split("_");
				if(jobName.length > 0){
					result = jobName[0];
				}
				
				if(result.length() > 13 || result.length() < 13 || result.matches(regex) == false){
					isValidJob = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidJob;
	}
	
	/**
	 *Validate 9 digit ISBN jobId
	 * @param jobId
	 * @return
	 */
	public boolean validate9DigitjobId(String jobId){
		boolean isValidJob = false;
		String result = "";
		String regex = "\\d+";
		try {
			if(jobId != null){
				String[] jobName = jobId.split("_");
				if(jobName.length > 0){
					result = jobName[0];
				}
				
				if(result.length() > 9 || result.length() < 9 || result.matches(regex) == false){
					isValidJob = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidJob;
	}
}
