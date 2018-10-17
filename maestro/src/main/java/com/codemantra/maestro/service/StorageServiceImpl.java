package com.codemantra.maestro.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.codemantra.maestro.dao.MasterDataDao;
import com.codemantra.maestro.dao.MetadataDao;
import com.codemantra.maestro.model.FileMetadata;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.util.ReadJobData;

@Service
public class StorageServiceImpl implements StorageService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	/*private static final Date utilDate = new Date();
	private static final Timestamp date = new Timestamp(utilDate.getTime());*/
	
	@Value("${storage.path}")
	String fileStoragePath;
	
	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	MetadataDao metadataDao;
	
	@Autowired
	MasterDataDao masterDataDao;
	
	@Autowired
	ReadJobData jobData;

	@Override
	public List<String> uploadJob(MultipartFile file) {
		LOGGER.info("Uploading file ....." + file.getOriginalFilename());
		
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		
		List<String> clientIdList = new ArrayList<String>();
		List<String> jobIdList = new ArrayList<String>();
		boolean jobProcess = false;
		String fileName = "";
		try {
			FileMetadata metadata = new FileMetadata();
			
			if(!file.getOriginalFilename().equals("")){
				fileName = file.getOriginalFilename();
				metadata.setFileName(fileName);
			}
			
			metadata.setCreatedOn(date);
			FileMetadata job = metadataDao.save(metadata);
			
			if(job != null){
				jobProcess = fileUploadProcess(job, file);
				if(jobProcess){
					//List<MasterData> jobList = readData(fileStoragePath+File.separator+job.getId()+File.separator+job.getFileName(), job);
					List<MasterData> jobList = jobData.validationJobList(fileStoragePath+File.separator+job.getId()+File.separator+job.getFileName(), job);
					
					for(MasterData data : jobList){
						clientIdList.add(data.getClientId());
						jobIdList.add(data.getJobId());
					}
					
					List<String> jobValidation = jobData.validateJobs(jobList);
					if(jobValidation.isEmpty()){
						LOGGER.info("Persist master data into database " +job.getId());
						
						List<MasterData> failedJobsList = masterDataService.checkFailedJobExists(clientIdList, jobIdList);
						if(failedJobsList != null && !failedJobsList.isEmpty()){
							//List<MasterData> masterDataList = readMetadata(fileStoragePath+File.separator+job.getId()+File.separator+job.getFileName(), job);
							List<MasterData> masterDataList = jobData.masterJobList(fileStoragePath+File.separator+job.getId()+File.separator+job.getFileName(), job);
							int updatedJobs = masterDataService.updateFailedJobs(clientIdList, jobIdList, masterDataList);
							if(updatedJobs > 0){
								LOGGER.info("Job updated successfully.");
							}
						}
						List<MasterData> masterDataList = jobData.masterJobList(fileStoragePath+File.separator+job.getId()+File.separator+job.getFileName(), job);
						
						for(MasterData data : masterDataList){
							MasterData jobExists = masterDataService.checkJobExists(data.getJobId(), data.getClientId());
							if(jobExists == null){
								masterDataDao.save(data);
							}
						}

						jobProcess = true;
						return jobValidation;
					}else{
						return jobValidation;
					}
						
				}
				/*}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param path
	 * @param job
	 * @return
	 */
	public List<MasterData> readData(String path, FileMetadata job){
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
	            
	            Cell cellValue0 = row.getCell(0, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue0 == null)
	            	masterData.setClientId(null);
	            else if("".equals(cellValue0.getStringCellValue().trim()))
	            	masterData.setClientId(null);
	            else 
	            	masterData.setClientId(row.getCell(0).getStringCellValue());
	            
	            DataFormatter fmt = new DataFormatter();
	            Double jobId = null;
	            
	            Cell cellValue1 = row.getCell(1, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue1 == null){
	            	masterData.setJobId(null);
	            }
	            /*else if(!row.getCell(0).getStringCellValue().equalsIgnoreCase("tf_encyclopedia")){
	                if("".equals(cellValue1.getStringCellValue().trim()))
	            	   masterData.setJobId(null);
	            }*/
	            else if(row.getCell(0).getStringCellValue().equalsIgnoreCase("tf_encyclopedia")){
	            	if(row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
	            		row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
	            		masterData.setJobId(row.getCell(1).getStringCellValue());
	            	}
	            }else{
	            	masterData.setJobId(row.getCell(1).getStringCellValue());
	            }
	            
	            Cell cellValue2 = row.getCell(2, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue2 == null)
	            	masterData.setStage1CopyEdit(null);
	            else if("".equals(cellValue2.getStringCellValue().trim()))
	            	masterData.setStage1CopyEdit(null);
	            else 
	            	masterData.setStage1CopyEdit(row.getCell(2).getStringCellValue());
	            
	            /*if(row.getCell(2).getStringCellValue() == null){
	            	masterData.setStage1CopyEdit("-");
	            }else{
	            	masterData.setStage1CopyEdit(row.getCell(2).getStringCellValue());
	            }*/
	            
	            Cell cellValue3 = row.getCell(3, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue3 == null)
	            	masterData.setStage2Graphics(null);
	            else if("".equals(cellValue3.getStringCellValue().trim()))
	            	masterData.setStage2Graphics(null);
	            else 
	            	masterData.setStage2Graphics(row.getCell(3).getStringCellValue());
	            
	            
	            Cell cellValue4 = row.getCell(4, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue4 == null)
	            	masterData.setStage3Equations(null);
	            else if("".equals(cellValue4.getStringCellValue().trim()))
	            	masterData.setStage3Equations(null);
	            else 
	            	masterData.setStage3Equations(row.getCell(4).getStringCellValue());
	            
	            
	            Cell cellValue5 = row.getCell(5, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue5 == null)
	            	masterData.setStage4TemplateName(null);
	            else if("".equals(cellValue5.getStringCellValue().trim()))
	            	masterData.setStage4TemplateName(null);
	            else 
	            	masterData.setStage4TemplateName(row.getCell(5).getStringCellValue());
	            
	            Cell cellValue6 = row.getCell(6, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue6 == null)
	            	masterData.setStage5TemplatePath(null);
	            else if("".equals(cellValue6.getStringCellValue().trim()))
	            	masterData.setStage5TemplatePath(null);
	            else 
	            	masterData.setStage5TemplatePath(row.getCell(6).getStringCellValue());
	            
	            
	            Cell cellValue7 = row.getCell(7, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue7 == null)
	            	masterData.setStage6MaestroMappingPath(null);
	            else if("".equals(cellValue7.getStringCellValue().trim()))
	            	masterData.setStage6MaestroMappingPath(null);
	            else 
	            	masterData.setStage6MaestroMappingPath(row.getCell(7).getStringCellValue());
	            
	            
	            Cell cellValue8 = row.getCell(8, Row.RETURN_NULL_AND_BLANK);
	            if (cellValue8 == null)
	            	masterData.setStage7StandardGenericStyleSheet(null);
	            else if("".equals(cellValue8.getStringCellValue().trim()))
	            	masterData.setStage7StandardGenericStyleSheet(null);
	            else 
	            	masterData.setStage7StandardGenericStyleSheet(row.getCell(8).getStringCellValue());
	            
	            
	            list.add(masterData);
	        }
		} catch (Exception e) {
			LOGGER.error("Error while checking duplicate jobs :: " +job.getId()+" "+job.getFileName());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Upload job file process
	 * @param job
	 * @param file
	 * @return
	 */
	public boolean fileUploadProcess(FileMetadata job, MultipartFile file){
		boolean uploadFile = false;
		String uploadPath = null;
		try {
			if(file.getOriginalFilename() != null && !file.isEmpty()){
				File fileUploadDir = new File(fileStoragePath+File.separator+job.getId());
				
				if(!fileUploadDir.exists())
					fileUploadDir.mkdirs();
				
				uploadPath = fileStoragePath+File.separator+job.getId()+File.separator+file.getOriginalFilename();
				file.transferTo(new File(uploadPath));
				
				uploadFile = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadFile;
	}
	
	/**
	 * Read the excel metadata
	 * @param path
	 * @param job
	 * @return
	 * @throws InvalidFormatException
	 */
	public List<MasterData> readMetadata(String path, FileMetadata job) throws InvalidFormatException{
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
	            masterData.setClientId(row.getCell(0).getStringCellValue());
	            
	            
	            if(row.getCell(0).getStringCellValue().equalsIgnoreCase("tf_encyclopedia")){
	            	//jobId = row.getCell(1).getNumericCellValue();
	            	
	            	if(row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
	            		row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
	            		masterData.setJobId(row.getCell(1).getStringCellValue());
	            	}
	            }else{
	            	masterData.setJobId(row.getCell(1).getStringCellValue());
	            }
	            
	            
	            if(row.getCell(2).getStringCellValue() != null){
	            	masterData.setStage1CopyEdit(row.getCell(2).getStringCellValue());
	            }else{
	            	masterData.setStage1CopyEdit("-");
	            }
	            if(row.getCell(3).getStringCellValue() != null){
	            	masterData.setStage2Graphics(row.getCell(3).getStringCellValue());
	            }else{
	            	masterData.setStage2Graphics("-");
	            }
	            if(row.getCell(4).getStringCellValue() != null){
	            	masterData.setStage3Equations(row.getCell(4).getStringCellValue());
	            }else{
	            	masterData.setStage3Equations("-");
	            }
	            if(row.getCell(5).getStringCellValue() != null){
	            	masterData.setStage4TemplateName(row.getCell(5).getStringCellValue());
	            }else{
	            	masterData.setStage4TemplateName("-");
	            }
	            if(row.getCell(6).getStringCellValue() != null){
	            	masterData.setStage5TemplatePath(row.getCell(6).getStringCellValue());
	            }else{
	            	masterData.setStage6MaestroMappingPath("-");
	            }
	            if(row.getCell(7).getStringCellValue() != null){
	            	masterData.setStage6MaestroMappingPath(row.getCell(7).getStringCellValue());
	            }else{
	            	masterData.setStage6MaestroMappingPath("-");
	            }
	            if(row.getCell(8).getStringCellValue() != null){
	            	masterData.setStage7StandardGenericStyleSheet(row.getCell(8).getStringCellValue());
	            }else{
	            	masterData.setStage7StandardGenericStyleSheet("-");
	            }
	            
	            jobId = row.getCell(1).getStringCellValue();
	            String[] isbn = jobId.split("_");
				String jobIsbn = isbn[0];
	            masterData.setJobIsbn(jobIsbn);
	            
	            Double noOfManuscripts = row.getCell(9).getNumericCellValue();
	            masterData.setNoOfManuscripts(noOfManuscripts.intValue());
	            masterData.setFileMetadata(job);
	            masterData.setJobStatus("IN-PROGRESS");
	            masterData.setCreatedOn(date);
	            masterData.setModifiedOn(date);
	            masterData.setCreatedBy(authentication.getName());
	            masterData.setNewJob(true);
	            masterDataList.add(masterData);
	        }

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return masterDataList;
	  }
	
	
}

