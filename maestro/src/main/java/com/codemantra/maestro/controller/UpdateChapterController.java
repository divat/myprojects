package com.codemantra.maestro.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.ClientDetails;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.service.ChapterDetailsService;
import com.codemantra.maestro.service.MasterDataService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Reset chapter status
 * controller
 * @author DHIVAKART
 *
 */

@ApiIgnore
@RestController
public class UpdateChapterController {

	private static final Logger log = LoggerFactory.getLogger(UpdateChapterController.class);
	
	@Autowired
	MasterDataService masterService;
	
	@Autowired
	ChapterDetailsService chapterDetailsService;
	
	/**
	 * Load job details
	 * @param jobId
	 * @return
	 */
	@GetMapping(value = "/getJobId")
	public ResponseEntity<List<MasterData>> loadJobId(@RequestParam String jobId){
		log.info("Loading the job id");
		try{
			List<MasterData> jobList = masterService.loadJob(jobId);
			return new ResponseEntity<List<MasterData>>(jobList, HttpStatus.OK);
		}catch(Exception e){
			log.error("Error while loading the job details :: " +e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Load client details
	 * @param clientId
	 * @return
	 */
	@GetMapping(value = "/getClientId")	
	public ResponseEntity<List<ClientDetails>> loadClientId(@RequestParam String customerId){
		log.info("Loading the client id");
		try{
			List<ClientDetails> clientList = masterService.loadClient(customerId);
			
			ClientDetails client = null;
			List<ClientDetails> clients = new ArrayList<ClientDetails>();
			
			return new ResponseEntity<List<ClientDetails>>(clientList, HttpStatus.OK);
		}catch(Exception e){
			log.error("Error while loading the job details :: " +e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Load chapters
	 * @param chapterName
	 * @return
	 */
	@GetMapping(value = "/getChapter")
	public ResponseEntity<List<ChapterDetails>> getChapterDetails(@RequestParam String chapterName){
		log.info("Loading the chapter details");
		try {
			List<ChapterDetails> chapterDetails = chapterDetailsService.loadChapterDetails(chapterName);
			return new ResponseEntity<List<ChapterDetails>>(chapterDetails, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while loading the chapter details :: " +e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
