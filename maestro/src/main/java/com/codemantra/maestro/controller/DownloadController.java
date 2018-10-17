package com.codemantra.maestro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codemantra.maestro.model.FileMetadata;
import com.codemantra.maestro.service.FileMetadataService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class DownloadController {
	
	private static final Logger log = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	FileMetadataService fileMetadataService;
	
	@Value("${job.feed.download.path}")
	private String jobFeedDownloadPath;
	
	@Value("${storage.path}")
	private String jobStoragePath;
	
	private static final String JOB_FEED_DOWNLOAD_PATH = "D:/Maestro_Operations/JobFeedDownload/test.xlsx";
	
	private InputStream fileInputStream;

	private String fileName;
	
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Method to download the job feed
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@GetMapping(value = "/jobFeedDownload")
	public void outputDownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Downloading the job feed....");
		try {
			this.setFileName("cM_Maestro-Job_Feed.xlsx");
			fileInputStream = new FileInputStream(new File(jobFeedDownloadPath +File.separator+ "cM_Maestro-Job_Feed.xlsx"));
			
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
		} catch (Exception e) {
			log.error("Error while downloading the job feed :: " +e.getMessage());
			e.printStackTrace();
		}finally{
			fileInputStream.close();
		}
	}
	
	/**
	 * File download
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@GetMapping(value = "download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("Downloading the file for " +request.getParameter("id"));
		try{
			FileMetadata metadata = fileMetadataService.findByMetadataId(Long.valueOf(request.getParameter("id")));
			this.setFileName(metadata.getFileName());
			
			fileInputStream = new FileInputStream(new File(jobStoragePath) +File.separator+ metadata.getId() +File.separator+ metadata.getFileName());
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
			
			int bytesRead = -1;
			if(fileInputStream != null){
				OutputStream outputStream = response.getOutputStream();
				
				byte[] buffer = new byte[BUFFER_SIZE];
				while((bytesRead = fileInputStream.read(buffer)) != -1){
					outputStream.write(buffer, 0 ,bytesRead);
				}
				outputStream.close();
			}
		}catch(Exception e){
			log.error("Error while downloading the input file for file id :: " +request.getParameter("id")+","+e.getMessage());
			e.printStackTrace();
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