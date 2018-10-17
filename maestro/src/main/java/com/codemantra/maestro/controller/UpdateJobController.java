package com.codemantra.maestro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.response.JobRequest;
import com.codemantra.maestro.response.StringResponse;
import com.codemantra.maestro.response.UpdatePathRequest;
import com.codemantra.maestro.service.MasterDataService;

/**
 * 
 * @author DHIVAKART
 *
 */
@RestController
public class UpdateJobController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateJobController.class);
	
	MasterDataService masterService;
	
	@Autowired
	public UpdateJobController(MasterDataService masterService){
		this.masterService = masterService;
	}
	
	/**
	 * Update the path details for job
	 * @param request
	 * @return
	 */
	@PutMapping(value = "/updateJobPath")
	public ResponseEntity<StringResponse> updateJobPathDetails(@RequestBody UpdatePathRequest request){
		logger.info("Updating the path details for job and client{} " + request.getJobId() +"," + request.getClientId());
		StringResponse response = new StringResponse();
		try {
			MasterData masterDataObj = masterService.checkJobExists(request.getJobId(), request.getClientId());
			if(masterDataObj != null){
				masterDataObj.setStage1CopyEdit(request.getCopyEditPath());
				masterDataObj.setStage3Equations(request.getEquationsPath());
				masterDataObj.setStage2Graphics(request.getGraphicsPath());
				masterDataObj.setStage5TemplatePath(request.getTemplatePath());
				masterDataObj.setStage6MaestroMappingPath(request.getMappingPath());
				masterDataObj.setStage7StandardGenericStyleSheet(request.getStyleSheetPath());
				int updatePathCount = masterService.updateJobPath(masterDataObj); 
				if(updatePathCount > 0){
					response.setId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setResponse("Job path updated.");
					response.setStatus(HttpStatus.OK);
				}else{
					response.setId(masterDataObj.getJobId());
					response.setClientId(masterDataObj.getClientId());
					response.setResponse("Job path not updated.");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setId(request.getJobId());
				response.setClientId(request.getClientId());
				response.setResponse("Job details not found.");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Exception while updating the path details :: " +request.getJobId());
			e.printStackTrace();
		}
		return new ResponseEntity<StringResponse>(response, HttpStatus.OK);
	}
}
