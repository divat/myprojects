package com.codemantra.maestro.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.MailConfig;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.response.ChapterEquationResponse;
import com.codemantra.maestro.response.ChapterUpdateResponse;
import com.codemantra.maestro.response.ChapterValStageResponse;
import com.codemantra.maestro.response.JobDetailsResponse;
import com.codemantra.maestro.response.JobRequest;
import com.codemantra.maestro.response.StringResponse;
import com.codemantra.maestro.response.TemplatePathResponse;
import com.codemantra.maestro.service.ChapterDetailsService;
import com.codemantra.maestro.service.MailConfigService;
import com.codemantra.maestro.service.MasterDataService;

/**
 * 
 * @author DHIVAKART
 *
 */
@RestController
public class WatchServiceController {

	private static final Logger logger = LoggerFactory.getLogger(WatchServiceController.class);
	private static final Date date = new Date();
	private static final Timestamp timestamp = new Timestamp(date.getTime());
	
	@Autowired
	MasterDataService masterService;
	
	@Autowired
	ChapterDetailsService chapterService;
	
	@Autowired
	MailConfigService mailConfigService;
	
	/**
	 * Return the list of jobs
	 * @return
	 */
	@GetMapping(value = "/getJobList")
	public ResponseEntity<?> getJobDetails(){
		logger.info("Fetching list of jobs...");
		List<JobDetailsResponse> list = new ArrayList<JobDetailsResponse>();
		
		StringResponse response = new StringResponse();
		String status = "";
		String value = "";
		try {
			status = "jobStatus";
			value = "IN-PROGRESS";
			
			List<MasterData> jobList = masterService.jobList(status, value);
			if(jobList != null && !jobList.isEmpty()){
				JobDetailsResponse jobDetails = null;
				for(MasterData job: jobList){
					jobDetails = new JobDetailsResponse();
					jobDetails.setSerialNo(job.getsId());
					jobDetails.setJobId(job.getJobId());
					jobDetails.setClientId(job.getClientId());
					jobDetails.setTemplateType(job.getTemplateType());
					if(job.getStage4TemplateName() != null){
						jobDetails.setTemplateName(job.getStage4TemplateName());
					}else{
						jobDetails.setTemplateName("");
					}
					/*jobDetails.setPath(job.getStage1CopyEdit());
					jobDetails.setGraphicsPath(job.getStage2Graphics());
					jobDetails.setEquationsPath(job.getStage3Equations());*/
					jobDetails.setNoOfManuScripts(String.valueOf(job.getNoOfManuscripts()));
					jobDetails.setNewJob(job.isNewJob());
					jobDetails.setStatus(HttpStatus.OK);
					list.add(jobDetails);
				}
				return new ResponseEntity<List<JobDetailsResponse>>(list, HttpStatus.OK);
			}else{
				response.setResponse("No job's found.");
				response.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<StringResponse>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error("Error while getting the list of master jobs :: " +e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Update the master job status
	 * @param jobId
	 * @param clientId
	 * @param status
	 * @return
	 */
	@PutMapping(value = "/updateJobStatus")
	public ResponseEntity<StringResponse> updateJobStatus(@RequestBody JobRequest request){
		logger.info("Updating the job status for " +request.getJobId()+","+request.getClientId());
		StringResponse response = new StringResponse();
		try {
			if(request.getStatus().equals("COMPLETED") || request.getStatus().equals("FAILED") || request.getStatus().equals("IN-PROGRESS")){
				MasterData masterDataObj = masterService.checkJobExists(request.getJobId(), request.getClientId());
				if(masterDataObj != null){
					int updateValue = masterService.updateJob(request.getJobId(), request.getClientId(), request.getStatus());
					if(updateValue > 0){
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setResponse("Updated successfully.");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setResponse("Failed to update the job.");
						response.setStatus(HttpStatus.NO_CONTENT);
					}
				}else{
					response.setId(request.getJobId());
					response.setClientId(request.getClientId());
					response.setResponse("Job details not found");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
		   }else{
				response.setId(request.getJobId());
				response.setClientId(request.getClientId());
				response.setResponse("Job status not valid.");
				response.setStatus(HttpStatus.NOT_FOUND);
		   }
		} catch (Exception e) {
			logger.error("Error while updating the status of master data :: " +request.getJobId());
			response.setId(request.getJobId());
			response.setClientId(request.getClientId());
			response.setResponse("Job details not found");
			response.setStatus(HttpStatus.CONFLICT);
			e.printStackTrace();
		}
		return new ResponseEntity<StringResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Update the chapter details job status
	 * @param jobId
	 * @param clientId
	 * @param status
	 * @return
	 */
	@PutMapping(value = "/updateChapterTemplateStatus")
	public ResponseEntity<StringResponse> updateChapterJobStatus(@RequestBody JobRequest chapter){
		logger.info("Updating the chapter job status for " +chapter.getJobId());
		StringResponse response = new StringResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(chapter.getJobId(), chapter.getClientId());
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter.getChapterName());
				if(chapterDetail != null){
					chapterDetail = updateChapterDetails(chapterDetail, chapter.getInDTemplateStatus());
					int updateStatus = chapterService.updateStatus(chapterDetail);
					if(updateStatus > 0){
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setResponse("Updated successfully.");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setResponse("Failed to update the job.");
						response.setStatus(HttpStatus.NO_CONTENT);
					}
				}else{
					response.setId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setResponse("Chapter details not found.");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setId(chapter.getJobId());
				response.setClientId(chapter.getClientId());
				response.setResponse("Job details not found.");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			logger.error("Error while updating the chapter job status :: " +chapter.getJobId()+","+chapter.getClientId());
			e.printStackTrace();
		}
		return new ResponseEntity<StringResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get chapter validation stage details 
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @return
	 */
	@GetMapping(value = "/getChapterTemplateStatus")
	public ResponseEntity<?> getChapterDetails(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter){
		logger.info("Fetching response for the chapter " +jobId+","+clientId);
		StringResponse resp = new StringResponse();
		ChapterValStageResponse response = new ChapterValStageResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(jobId, clientId);
			//ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(jobId, chapter);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setChapterName(chapterDetail.getChapterName());
					/*response.setInDesignImportMap(String.valueOf(chapterDetail.getStageWsInDImportMap()));
					response.setInDesignStyleChecking(String.valueOf(chapterDetail.getStageWsStyleChecking()));*/
					response.setTemplateStatus(String.valueOf(chapterDetail.getInDStyleMapStatus()));
					response.setStatus(HttpStatus.OK);
					return new ResponseEntity<ChapterValStageResponse>(response, HttpStatus.OK);
				}else{
					resp.setId(masterDataObj.getJobId());
					resp.setClientId(masterDataObj.getClientId());
					resp.setResponse("Chapter details not found for " +jobId+","+clientId);
					resp.setStatus(HttpStatus.NOT_FOUND);
					return new ResponseEntity<StringResponse>(resp, HttpStatus.OK);
				}
				
			}else{
				resp.setId(jobId);
				resp.setClientId(clientId);
				resp.setResponse("Chapter details not found for " +jobId+","+clientId);
				resp.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<StringResponse>(resp, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error("Error while getting the chapter response :: " +e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Update the chapters completion status
	 * @param jobId
	 * @param clientId
	 * @return
	 */
	@PutMapping(value = "/updateStatus")
	public ResponseEntity<?> updateJobChapterStatus(@RequestBody JobRequest request){
		logger.info("Updating the chapter completion job status for " +request.getJobId());
		StringResponse response = new StringResponse();
		ChapterUpdateResponse updateResponse = new ChapterUpdateResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(request.getJobId(), request.getClientId());
			if(masterDataObj != null){
				List<ChapterDetails> details = chapterService.checkChapterDetailsExists(masterDataObj);
				if(details != null && !details.isEmpty()){
					//send missing chapter when count not matches 
					//process update status
					Integer noOfChapters = masterDataObj.getNoOfManuscripts();
					Integer chapterCount = details.size();
					Integer missingChapter = 0;
					
					List<ChapterDetails> statusUpdateList = chapterService.updateReadyStatus(details, request.getChapterDate(), request.getStyleSheetModifiedDate());
					
					updateResponse.setJobId(masterDataObj.getJobId());
					updateResponse.setClientId(masterDataObj.getClientId());
					if(noOfChapters != chapterCount){
						missingChapter = noOfChapters - chapterCount;
						updateResponse.setChaptersMissing(missingChapter);
					}else{
						updateResponse.setChaptersMissing(0);
					}
					
					updateResponse.setMessage("Chapter's status updated successfully");
					updateResponse.setChaptersList(statusUpdateList);
					updateResponse.setStatus(HttpStatus.OK);
					return new ResponseEntity<ChapterUpdateResponse>(updateResponse, HttpStatus.OK); 
				}else{
					response.setId(request.getJobId());
					response.setClientId(request.getClientId());
					response.setResponse("Chapter details not found.");
					response.setStatus(HttpStatus.NOT_FOUND);
					return new ResponseEntity<StringResponse>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get mail-config details
	 * @param groupName
	 * @return
	 */
	@GetMapping(value = "/getMailConfig")
	public ResponseEntity<MailConfig> getMailConfigDetails(@RequestParam(value="groupName", required=true) String groupName){
		MailConfig mailConfig = null;
		try {
			 mailConfig = mailConfigService.getMailConfig(groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MailConfig>(mailConfig, HttpStatus.OK);
	}
	
	/**
	 * Update template status for chapter
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/updateTemplateStatus")
	public ResponseEntity<?> updateInDStyleChecking(@RequestBody JobRequest request){
		logger.info("Updating template status for " +request.getJobId()+","+request.getClientId());
		StringResponse response = new StringResponse();
		int updateStatus = 0;
		try {
			MasterData masterDataObj = masterService.checkJobExists(request.getJobId(), request.getClientId());
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), request.getChapterName());
				if(chapterDetail != null){
					if(request.getInDTemplateStatus().equals("true")){
						chapterDetail = updateChapterDetails(chapterDetail, request.getInDTemplateStatus(), request.getComments());
						updateStatus = chapterService.updateTemplateStatus(chapterDetail);
					}else{
						chapterDetail = updateChapterDetails(chapterDetail, request.getInDTemplateStatus());
						updateStatus = chapterService.updateStatus(chapterDetail);
					}
					
					if(updateStatus > 0){
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setInDTemplateStatus(String.valueOf(chapterDetail.getInDStyleMapStatus()));
						response.setResponse("Updated successfully.");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setId(masterDataObj.getJobId());
						response.setClientId(masterDataObj.getClientId());
						response.setResponse("Failed to update the job.");
						response.setStatus(HttpStatus.NO_CONTENT);
					}
				}else{
					response.setId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setResponse("Chapter details not found.");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setId(request.getJobId());
				response.setClientId(request.getClientId());
				response.setResponse("Job details not found");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while updating the template status :: " +e.getMessage());
			response.setId(request.getJobId());
			response.setClientId(request.getClientId());
			response.setResponse("Error");
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<StringResponse>(response, HttpStatus.OK); 
	}
	
	@GetMapping(value = "/getTemplateStatus")
	public ResponseEntity<?> getChapterTemplateDetails(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter){
		logger.info("Fetching response for the chapter " +jobId+","+clientId);
		StringResponse resp = new StringResponse();
		ChapterValStageResponse response = new ChapterValStageResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(jobId, clientId);
			//ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(jobId, chapter);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setChapterName(chapterDetail.getChapterName());
					/*response.setInDesignImportMap(String.valueOf(chapterDetail.getStageWsInDImportMap()));
					response.setInDesignStyleChecking(String.valueOf(chapterDetail.getStageWsStyleChecking()));*/
					response.setTemplateStatus(String.valueOf(chapterDetail.getInDStyleMapStatus()));
					if(chapterDetail.getInDStyleMapStatus() == true){
						response.setComments(chapterDetail.getInDStyleMapComments());
					}
					response.setStatus(HttpStatus.OK);
					return new ResponseEntity<ChapterValStageResponse>(response, HttpStatus.OK);
				}else{
					resp.setId(masterDataObj.getJobId());
					resp.setClientId(masterDataObj.getClientId());
					resp.setResponse("Chapter details not found for " +jobId+","+clientId);
					resp.setStatus(HttpStatus.NOT_FOUND);
					return new ResponseEntity<StringResponse>(resp, HttpStatus.OK);
				}
				
			}else{
				resp.setId(jobId);
				resp.setClientId(clientId);
				resp.setResponse("Job details not found for " +jobId+","+clientId);
				resp.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<StringResponse>(resp, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error("Error while getting the chapter response :: " +e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get template path details
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @return
	 */
	@GetMapping(value="/getTemplatePathDetails")
	public ResponseEntity<TemplatePathResponse> getPathDetails(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId){
		logger.info("Fetching template path details for " +jobId);
		TemplatePathResponse response = new TemplatePathResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				/*ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);*/
				/*if(chapterDetail != null){*/
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setTemplateName(masterDataObj.getStage4TemplateName());
					response.setTemplatePath(masterDataObj.getStage5TemplatePath());
					response.setMaestroMappingPath(masterDataObj.getStage6MaestroMappingPath());
					response.setStyleSheetPath(masterDataObj.getStage7StandardGenericStyleSheet());
					response.setStatus(HttpStatus.OK);
				/*}else{
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setResponse("Chapter details not found for " +jobId+","+clientId);
					response.setStatus(HttpStatus.NOT_FOUND);
				}*/
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setResponse("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while fetching the template path details :: " +e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setResponse("Error for " +jobId+","+clientId);
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TemplatePathResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the chapter equation details
	 * for job and client id
	 * @param jobId
	 * @param clientId
	 * @param chapterName
	 * @return
	 */
	@GetMapping(value = "/getChapterEquation")
	public ResponseEntity<ChapterEquationResponse> getChapterEquation(@RequestParam("jobId") String jobId, @RequestParam("clientId") String clientId,
				@RequestParam("chapter") String chapterName){
		logger.info("Getting the equation details for " +jobId+","+clientId+","+chapterName);
		ChapterEquationResponse response = new ChapterEquationResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapterName);
				if(chapterDetail != null){
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setChapterName(chapterDetail.getChapterName());
					response.setIsEquationExists(String.valueOf(chapterDetail.ismEquation()));
					response.setStatus(HttpStatus.OK);
				}else{
					response.setJobId(jobId);
					response.setClientId(clientId);
					response.setMessage("Chapter details not found for " +jobId+","+clientId+","+chapterName);
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while getting the equation details for " +jobId+","+clientId);
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error while getting the equation details for " +jobId+","+clientId);
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ChapterEquationResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Update indesign template status
	 * against chapter
	 * @param chapterDetail
	 * @param inDStyleStage
	 * @param inDImportMap
	 * @return
	 */
	private ChapterDetails updateChapterDetails(ChapterDetails chapterDetail, String templateStatus){
		ChapterDetails detail = new ChapterDetails();
		try {
			detail.setMetadataJobId(chapterDetail.getMetadataJobId());
			detail.setChapterName(chapterDetail.getChapterName());
			if(templateStatus != null)
				detail.setInDStyleMapStatus((Boolean.valueOf(templateStatus)));
			else
				detail.setInDStyleMapStatus(false);
			detail.setModifiedOn(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}
	
	/**
	 * Update indesign template status
	 * and comments against chapter
	 * @param chapterDetail
	 * @param templateStatus
	 * @param comments
	 * @return
	 */
	private ChapterDetails updateChapterDetails(ChapterDetails chapterDetail, String templateStatus, String comments){
		ChapterDetails detail = new ChapterDetails();
		try {
			detail.setMetadataJobId(chapterDetail.getMetadataJobId());
			detail.setChapterName(chapterDetail.getChapterName());
			if(templateStatus != null)
				detail.setInDStyleMapStatus((Boolean.valueOf(templateStatus)));
			else
				detail.setInDStyleMapStatus(false);
			detail.setInDStyleMapComments(comments);
			detail.setModifiedOn(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}
	
	/**
	 * Update the chapter details indesign stage
	 * @param chapterDetail
	 * @param inDStyleStage
	 * @param inDImportMap
	 * @return
	 *//*
	private ChapterDetails updateChapterDetails(ChapterDetails chapterDetail, String inDStyleStage, String inDImportMap){
		ChapterDetails detail = new ChapterDetails();
		try {
			detail.setMetadataJobId(chapterDetail.getMetadataJobId());
			detail.setChapterName(chapterDetail.getChapterName());
			if(inDStyleStage != null)
				detail.setStageWsStyleChecking((Boolean.valueOf(inDStyleStage)));
			else
				if(chapterDetail.getStageWsStyleChecking() != true)
					detail.setStageWsStyleChecking(false);
				else
					detail.setStageWsStyleChecking(true);
			
			if(inDImportMap != null)
				detail.setStageWsInDImportMap((Boolean.valueOf(inDImportMap)));
			else
				if(chapterDetail.getStageWsInDImportMap() != true)
					detail.setStageWsInDImportMap(false);
				else
					detail.setStageWsInDImportMap(true);
			detail.setModifiedOn(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}*/
}
