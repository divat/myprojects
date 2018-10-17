package com.codemantra.maestro.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.response.PreEditingResponse;
import com.codemantra.maestro.response.PreEditingValResponse;
import com.codemantra.maestro.service.ChapterDetailsService;
import com.codemantra.maestro.service.MasterDataService;
import com.codemantra.maestro.service.StorageService;

import scala.annotation.varargs;

@Controller
public class PreEditingController {
	private static final Logger logger = LoggerFactory.getLogger(PreEditingController.class);

	private static final Date date = new Date();
	private static final Timestamp timestamp = new Timestamp(date.getTime());
	@Autowired
	StorageService storageService;
	
	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	ChapterDetailsService chapterDetailsService;
	
	/*@RequestMapping(value="/",method = RequestMethod.GET)
    public String home(){
        return "index";
    }*/
		
	/**
	 * Pre editing process 
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @param cleanUpStage
	 * @param docVal
	 * @param structureVal
	 * @param postVal
	 * @param postConv
	 * @return
	 */
	@RequestMapping(value = "/preEditValProcess", method = RequestMethod.POST)
	public ResponseEntity<PreEditingResponse> preEditingValidation(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter, @RequestParam(name="cleanUpStage", required=false) String cleanUpStage, @RequestParam(name="docVal", required=false) String docVal,
			@RequestParam(name="structureVal", required=false) String structureVal, @RequestParam(name="postVal", required=false) String postVal, @RequestParam(name="postConv", required=false) String postConv){
		logger.info("Pre-editing validation process for job " +jobId+","+clientId);
		PreEditingResponse response = new PreEditingResponse();
		ChapterDetails chapterDetails = new ChapterDetails();
		try {
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				
				ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					logger.info("Updating chapter :: " +chapter);
					chapterDetail = updateChapterDetails(chapterDetail,cleanUpStage,docVal,structureVal,postVal,postConv);
					//chapterDetail.setId(chapterDetail.getId());
					int update = chapterDetailsService.update(chapterDetail);
					if(update > 0){
						response.setId(Long.valueOf(masterDataObj.getId()));
						response.setJobId(masterDataObj.getJobId());
						response.setValue(chapterDetail.getChapterName());
						response.setMessage("Updated successfully");
						response.setStatus(HttpStatus.OK);
						logger.info("Updated chapter :: " +chapter);
						return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
					}
				}else{
					logger.info("Inserting new Chapter details :: " +chapter);
					
					Integer noOfManuscripts = masterDataObj.getNoOfManuscripts();
					Long chapterCount = chapterDetailsService.getChapterDetailsCount(String.valueOf(masterDataObj.getId()));
					
					if(chapterCount < noOfManuscripts){
						chapterDetails.setMetadataJobId(masterDataObj);
						//chapterDetails.setMetadataClientId(masterDataObj);
						chapterDetails.setChapterName(chapter);
						if(cleanUpStage != null)
							chapterDetails.setStageCleanUp(Boolean.valueOf(cleanUpStage));
						else
							chapterDetails.setStageCleanUp(false);
						if(docVal != null)
							chapterDetails.setDocValidation(Boolean.valueOf(docVal));
						else
							chapterDetails.setDocValidation(false);
						if(structureVal != null)
							chapterDetails.setStructuringVal(Boolean.valueOf(structureVal));
						else
							chapterDetails.setStructuringVal(false);
						if(postVal != null)
							chapterDetails.setPostVal(Boolean.valueOf(postVal));
						else
							chapterDetails.setPostVal(false);
						if(postConv != null)
							chapterDetails.setPostConv(Boolean.valueOf(postConv));
						else
							chapterDetails.setPostConv(false);
						
						chapterDetails.setCreatedOn(timestamp);
						
						ChapterDetails obj = masterDataService.saveChapterInfo(chapterDetails);
						if(obj != null){
							response.setId(Long.valueOf(obj.getId()));
							response.setJobId(masterDataObj.getJobId());
							response.setValue(obj.getChapterName());
							response.setMessage("Saved successfully");
							response.setStatus(HttpStatus.OK);
							logger.info("Inserted new Chapter details :: " +chapter);
							return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
						}
					}else{
						response.setJobId(masterDataObj.getJobId());
						response.setMessage("Chapter details cannot be greater than no of manuscripts.");
						response.setStatus(HttpStatus.OK);
						logger.info("Chapter details cannot be greater than no of manuscripts. :: " +chapter);
						return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
					}
				}
			}else{
				//response.setId(Long.valueOf(masterDataObj.getId()));
				logger.info("Job details not found for :: " +jobId+"-"+clientId);
				response.setJobId(jobId);
				response.setValue(chapter);
				response.setMessage("Job details not found " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.info("Exception in pre-editing validation process :: " +jobId+","+clientId);
			logger.info("Error in pre-editing validation process :: ", e.getMessage());
			e.printStackTrace();
			response.setJobId(jobId);
			response.setValue(chapter);
			response.setMessage("Failed to update for " +jobId+","+clientId);
			response.setStatus(HttpStatus.NOT_FOUND);
			return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
		}
		return null;
	}
	
	/**
	 * Get validation stage details
	 * for chapter
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @return
	 */
	@RequestMapping(value = "/getValStageDetails", method = RequestMethod.GET)
	public ResponseEntity<PreEditingValResponse> getChapterDetails(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter){
		logger.info("Fetching response for the chapter " +jobId+","+chapter);
		PreEditingValResponse response  = new PreEditingValResponse();;
		try {
			
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					response.setJobId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setChapterName(chapterDetail.getChapterName());
					response.setStageCleanUp(String.valueOf(chapterDetail.getStageCleanUp()));
					response.setDocVal(String.valueOf(chapterDetail.getDocValidation()));
					response.setStructuringVal(String.valueOf(chapterDetail.getStructuringVal()));
					response.setPostVal(String.valueOf(chapterDetail.getPostVal()));
					response.setPostConv(String.valueOf(chapterDetail.getPostConv()));
					response.setInDStyleMap(String.valueOf(chapterDetail.getInDStyleMapStatus()));
					response.setWdExportMap(String.valueOf(chapterDetail.isWordExportMapStatus()));
					response.setStatus(HttpStatus.OK);
					/*response.setJobId(jobId);
					response.setValue(chapter);
					response.setMessage("test");
					response.setStatus(HttpStatus.FOUND);*/
					//return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
				}else{
					response.setJobId(jobId);
					response.setValue(chapter);
					response.setMessage("Chapter details not found for " +jobId+","+clientId);
					response.setStatus(HttpStatus.NOT_FOUND);
					//return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
				}
			}else{
				response.setJobId(jobId);
				response.setValue(chapter);
				response.setMessage("Job details not found");
				response.setStatus(HttpStatus.NOT_FOUND);
				//return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error("Error while getting the chapter response :: " +e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the maestro path details
	 * @param jobId
	 * @param clientId
	 * @param copyEditPath
	 * @return
	 */
	@GetMapping(value="/getMaestroPath")
	public ResponseEntity<PreEditingValResponse> getMaestroPath(@RequestParam(value="jobId", required=true) String jobId, 
			@RequestParam(value="clientId", required=true) String clientId){
		logger.info("Getting the maestro path details for " +jobId+","+clientId);
		PreEditingValResponse response = new PreEditingValResponse();
		try {
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				response.setJobId(masterDataObj.getJobId());
				response.setClientId(masterDataObj.getClientId());
				response.setMaestroMappingPath(masterDataObj.getStage6MaestroMappingPath());
				response.setStatus(HttpStatus.OK);
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while getting the maestro path details :: " +e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error for " +jobId+","+clientId);
			response.setStatus(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the copy edit path details
	 * @param jobId
	 * @param clientId
	 * @param copyEditPath
	 * @return
	 */
	@GetMapping(value="/getCopyEditPath")
	public ResponseEntity<PreEditingValResponse> getCopyEditPath(@RequestParam(value="jobId", required=true) String jobId, 
			@RequestParam(value="clientId", required=true) String clientId){
		logger.info("Getting the copy edit path details for " +jobId+","+clientId);
		PreEditingValResponse response = new PreEditingValResponse();
		try {
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				response.setJobId(masterDataObj.getJobId());
				response.setClientId(masterDataObj.getClientId());
				response.setCopyEditPath(masterDataObj.getStage1CopyEdit());
				response.setStatus(HttpStatus.OK);
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while getting the copy edit path details :: " +e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error for " +jobId+","+clientId);
			response.setStatus(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the graphics path details
	 * @param jobId
	 * @param clientId
	 * @param copyEditPath
	 * @return
	 */
	@GetMapping(value="/getGraphicsPath")
	public ResponseEntity<PreEditingValResponse> getGraphicsPath(@RequestParam(value="jobId", required=true) String jobId, 
			@RequestParam(value="clientId", required=true) String clientId){
		logger.info("Getting the graphics path details for " +jobId+","+clientId);
		PreEditingValResponse response = new PreEditingValResponse();
		try {
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				response.setJobId(masterDataObj.getJobId());
				response.setClientId(masterDataObj.getClientId());
				if(masterDataObj.getStage2Graphics() != null && masterDataObj.getStage2Graphics() != ""){
					response.setGraphicsPath(masterDataObj.getStage2Graphics());
				}else{
					response.setGraphicsPath("-");
				}
				response.setStatus(HttpStatus.OK);
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while getting the copy edit path details :: " +e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error for " +jobId+","+clientId);
			response.setStatus(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the graphics path details
	 * @param jobId
	 * @param clientId
	 * @param copyEditPath
	 * @return
	 */
	@GetMapping(value="/getEquationsPath")
	public ResponseEntity<PreEditingValResponse> getEquationsPath(@RequestParam(value="jobId", required=true) String jobId, 
			@RequestParam(value="clientId", required=true) String clientId){
		logger.info("Getting the graphics path details for " +jobId+","+clientId);
		PreEditingValResponse response = new PreEditingValResponse();
		try {
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				response.setJobId(masterDataObj.getJobId());
				response.setClientId(masterDataObj.getClientId());
				if(masterDataObj.getStage3Equations() != null && masterDataObj.getStage3Equations() != ""){
					response.setEquationsPath(masterDataObj.getStage3Equations());
				}else{
					response.setEquationsPath("-");
				}
				response.setStatus(HttpStatus.OK);
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error while getting the copy edit path details :: " +e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error for " +jobId+","+clientId);
			response.setStatus(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Update math equation for chapter
	 * details
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @param equationExists
	 * @return
	 */
	@RequestMapping(value = "/updateChapterEquation", method=RequestMethod.POST)
	public ResponseEntity<?> updateChapterEquation(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter, @RequestParam(name="mEquationExists") String equationExists){
		logger.info("Update path details for " +jobId+","+clientId+","+chapter);
		PreEditingValResponse response = new PreEditingValResponse();
		try{
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					int update = chapterDetailsService.updateEquation(chapterDetail, Boolean.valueOf(equationExists));
					if(update > 0){
						response.setJobId(jobId);
						response.setClientId(clientId);
						response.setMessage("Updated successfully");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setJobId(jobId);
						response.setClientId(clientId);
						response.setMessage("Failed to update");
						response.setStatus(HttpStatus.NOT_MODIFIED);
					}
				}else{
					response.setJobId(jobId);
					response.setClientId(clientId);
					response.setMessage("Chapter details not found for " +jobId+","+clientId);
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			logger.error("Error while updating the equation  " +jobId+","+clientId+","+e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error while updating the equation " +jobId+","+clientId);
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Update word map status
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @param wordMapStatus
	 * @return
	 */
	@RequestMapping(value = "/updateWordExportMap", method=RequestMethod.POST)
	public ResponseEntity<?> updateWordMapStatus(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter, @RequestParam(name="WdExportMapStatus") String wordMapStatus){
		logger.info("Update path details for " +jobId+","+clientId+","+chapter);
		PreEditingValResponse response = new PreEditingValResponse();
		try{
			MasterData masterDataObj = masterDataService.checkJobExists(jobId, clientId);
			if(masterDataObj != null){
				ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapter);
				if(chapterDetail != null){
					int update = chapterDetailsService.updateWordMapStatus(chapterDetail, Boolean.valueOf(wordMapStatus));
					if(update > 0){
						response.setJobId(jobId);
						response.setClientId(clientId);
						response.setMessage("Word export map updated successfully");
						response.setStatus(HttpStatus.OK);
					}else{
						response.setJobId(jobId);
						response.setClientId(clientId);
						response.setMessage("Failed to update word map.");
						response.setStatus(HttpStatus.NOT_MODIFIED);
					}
				}else{
					response.setJobId(jobId);
					response.setClientId(clientId);
					response.setMessage("Chapter details not found for " +jobId+","+clientId);
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setJobId(jobId);
				response.setClientId(clientId);
				response.setMessage("Job details not found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			logger.error("Error while updating the word map  " +jobId+","+clientId+","+e.getMessage());
			response.setJobId(jobId);
			response.setClientId(clientId);
			response.setMessage("Error while updating the word map " +jobId+","+clientId);
			response.setStatus(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PreEditingValResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param jobId
	 * @param clientId
	 * @param chapter
	 * @param cleanUpStage
	 * @param docVal
	 * @param structureVal
	 * @param postVal
	 * @param postConv
	 * @return
	 */
	@GetMapping(value = "/getCleanUpStage")
	public ResponseEntity<PreEditingResponse> getChapterDetails(@RequestParam(name="jobId", required=true) String jobId, @RequestParam(name="clientId", required=true) String clientId,
			@RequestParam(name="chapter", required=true) String chapter, @RequestParam(name="cleanUpStage", required=false) String cleanUpStage, @RequestParam(name="docVal", required=false) String docVal,
			@RequestParam(name="structureVal", required=false) String structureVal, @RequestParam(name="postVal", required=false) String postVal, @RequestParam(name="postConv", required=false) String postConv){
		logger.info("Fetching response for the chapter " +jobId+","+chapter);
		PreEditingResponse response = new PreEditingResponse();
		try {
			ChapterDetails chapterDetail = chapterDetailsService.isChapterDetailsExists(jobId, chapter);
			if(chapterDetail != null){
				ChapterDetails chapterDetails = chapterDetailsService.getChapterByIdAndStage(jobId, chapter,cleanUpStage);
				response.setId(chapterDetails.getId());
				response.setValue(String.valueOf(chapterDetails.getStageCleanUp()));
				response.setMessage("Stage1 value found for " +jobId+","+clientId);
				response.setStatus(HttpStatus.FOUND);
				return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
			}else{
				response.setJobId(jobId);
				response.setValue(chapter);
				response.setMessage("Job details not found " +jobId+","+clientId);
				response.setStatus(HttpStatus.NOT_FOUND);
				return new ResponseEntity<PreEditingResponse>(response, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method to update the chapter details
	 * @param chapterDetail
	 * @param cleanupstage
	 * @param docVal
	 * @param structureVal
	 * @param postVal
	 * @param postConv
	 * @return
	 */
	private ChapterDetails updateChapterDetails(ChapterDetails chapterDetail,String cleanupstage,String docVal,String structureVal,String postVal,String postConv){
		logger.info("Updating the chapter details");
		ChapterDetails details = new ChapterDetails();
		try {
			details.setMetadataJobId(chapterDetail.getMetadataJobId());
			details.setChapterName(chapterDetail.getChapterName());
			if(cleanupstage != null)
				details.setStageCleanUp(Boolean.valueOf(cleanupstage));
			else
				if(chapterDetail.getStageCleanUp() != true)
					details.setStageCleanUp(false);
				else
					details.setStageCleanUp(true);
			if(docVal != null)
				details.setDocValidation(Boolean.valueOf(docVal));
			else
				if(chapterDetail.getDocValidation() != true)
					details.setDocValidation(false);
				else
					details.setDocValidation(true);
			if(structureVal != null)
				details.setStructuringVal(Boolean.valueOf(structureVal));
			else
				if(chapterDetail.getStructuringVal() != true)
					details.setStructuringVal(false);
				else
					details.setStructuringVal(true);
			if(postVal != null)
				details.setPostVal(Boolean.valueOf(postVal));
			else
				if(chapterDetail.getPostVal() != true)
					details.setPostVal(false);
				else
					details.setPostVal(true);
			if(postConv != null)
				details.setPostConv(Boolean.valueOf(postConv));
			else
				if(chapterDetail.getPostConv() != true)
					details.setPostConv(false);
				else
					details.setPostConv(true);
			
			details.setModifiedOn(timestamp);
		} catch (Exception e) {
			logger.error("Error while updating the chapter details :: " +e.getMessage());
			e.printStackTrace();
		}
		return details;
		
	}
}
