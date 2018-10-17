package com.codemantra.maestro.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.codemantra.maestro.model.AddManuscripts;
import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.MasterData;
import com.codemantra.maestro.model.Stage;
import com.codemantra.maestro.service.ChapterDetailsService;
import com.codemantra.maestro.service.MasterDataService;
import com.codemantra.maestro.service.StorageService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
	private static final Date date = new Date();
	private static final Timestamp timestamp = new Timestamp(date.getTime());

	@Autowired
	StorageService storageService;

	@Autowired
	MasterDataService masterDataService;

	@Autowired
	ChapterDetailsService chapterService;

	/**
	 * Render login page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			model.setViewName("index");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public ModelAndView errorLogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		model.addObject("error", "Invalid username and password!");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			// System.out.println("Authenticated username :: "
			// +currentUserName);
			model.setViewName("index");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	/**
	 * Render home page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		HttpSession session = request.getSession();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			/*
			 * com.codemantra.maestro.model.Users account =
			 * loginService.getUserType(currentUserName);
			 */
			/*
			 * List<Screen> screenList =
			 * loginService.getScreenList(currentUserName);
			 */
			/*
			 * session.setAttribute("screenList", screenList);
			 * session.setAttribute("userType",
			 * account.getUserType().getUserTypeId());
			 */
			//session.setAttribute("userId", currentUserName);
			// return new ModelAndView("site.homepage", "screenList",
			// screenList);
			return new ModelAndView("index");
		} else {
			return new ModelAndView("login");
		}
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	/**
	 * Method to upload bulk job
	 * 
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadMasterFile(@RequestParam("file") MultipartFile file, Model model) {
		logger.info("Uploading file :: " + file.getOriginalFilename());
		ModelAndView mv = new ModelAndView("index");
		String responseMsg = null;
		String msg = null;
		boolean uploadProcess = false;
		try {
			if (file.isEmpty()) {
				msg = "Please upload the file.";
				model.addAttribute("responseMsg", msg);
				return mv;
			} else {
				/*
				 * Map<String, List<String>> map =
				 * storageService.uploadJob(file);
				 * 
				 * String key = ""; List<String> value = null; for
				 * (Map.Entry<String, List<String>> entry : map.entrySet()) {
				 * key = entry.getKey(); value = entry.getValue(); }
				 */
				List<String> uploadJobList = storageService.uploadJob(file);
				Set<String> set = new HashSet<>();
				set.addAll(uploadJobList);
				uploadJobList.clear();
				uploadJobList.addAll(set);
				if(uploadJobList != null && !uploadJobList.isEmpty()){
					model.addAttribute("responseMsg", uploadJobList);
				}else{
					responseMsg = "Job successfully added.";
					model.addAttribute("responseMsg", responseMsg);
				}
				/*if (uploadProcess) {
					responseMsg = "Job successfully added.";
					model.addAttribute("responseMsg", responseMsg);
				} else {
					responseMsg = "Duplicate jobs found.";
					model.addAttribute("responseMsg", responseMsg);
				}*/
				return mv;
			}
		} catch (Exception e) {
			logger.error("File upload failed :: " + e.getMessage());
			model.addAttribute("Something wrong happened.", responseMsg);
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * List jobs
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/getJobs")
	public String getMasterJobs(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Fetching the master jobs ");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String jsonOutput = "";
		try {

			List<MasterData> jobList = masterDataService.jobList(authentication.getName());
			if (jobList != null && !jobList.isEmpty()) {
				for (MasterData data : jobList) {
					if (!jsonOutput.equalsIgnoreCase(""))
						jsonOutput += ",[";
					else
						jsonOutput += "[";
					jsonOutput += "\"" + data.getsId() + "\"";
					jsonOutput += ",\"<a href='' onclick='javascript:popup(" + data.getId() + ");'>" + data.getJobId()
							+ "</a>\"";
					jsonOutput += ",\"" + data.getClientId() + "\"";
					jsonOutput += ",\"" + data.getTemplateType() + "\"";
					/* jsonOutput += ",\""+data.getStage1CopyEdit()+"\""; */
					/*
					 * jsonOutput += ",\""+data.getStage2Graphics()+"\"";
					 * jsonOutput += ",\""+data.getStage3Equations()+"\"";
					 * jsonOutput += ",\""+data.getStage4TemplateName()+"\"";
					 * jsonOutput += ",\""+data.getStage5TemplatePath()+"\"";
					 * jsonOutput +=
					 * ",\""+data.getStage6MaestroMappingPath()+"\""; jsonOutput
					 * += ",\""+data.getStage7StandardGenericStyleSheet()+"\"";
					 */
					jsonOutput += ",\"" + data.getNoOfManuscripts() + "\"";
					jsonOutput += ",\"" + data.getJobStatus() + "\"";
					if(data.getModifiedOn() != null){
						String jobModifiedDate = sdf.format(data.getModifiedOn());
						jsonOutput += ",\"" + data.getModifiedOn() + "\"";
					}else{
						String jobCreatedDate = sdf.format(data.getCreatedOn());
						jsonOutput += ",\"" + data.getModifiedOn() + "\"";
					}
					jsonOutput += ",\"<a href='download?id="+data.getFileMetadata().getId()+"'>Download</a>\"";
					jsonOutput += "]";
				}
			}
			response.getWriter().write("{\"data\":[" + jsonOutput + "]}");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Render chapter details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/chapterDetails")
	public ModelAndView jobChapterDetailsView(HttpServletRequest request, Model model) {
		logger.info("Fetching chapter details for " + request.getParameter("jobId"));
		ModelAndView mav = new ModelAndView("job-chapter-details");
		try {
			Integer jobId = (Integer.parseInt(request.getParameter("jobId")));
			model.addAttribute("jobId", jobId);
		} catch (Exception e) {
			logger.error("Error while fetching the chapter details for job :: " + request.getParameter("jobId")
					+ e.getMessage());
		}
		return mav;
	}

	/**
	 * Chapter details list for job
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/jobChapterDetails")
	public String jobChapterList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Job chapter list " + request.getParameter("jobId"));
		String jsonOutput = "";
		try {
			List<ChapterDetails> chapterDetails = chapterService
					.chapterList(Long.valueOf(request.getParameter("jobId")));
			if (chapterDetails != null && !chapterDetails.isEmpty()) {
				for (ChapterDetails chapter : chapterDetails) {
					if (!jsonOutput.equalsIgnoreCase(""))
						jsonOutput += ",[";
					else
						jsonOutput += "[";
						jsonOutput += "\"" + chapter.getChapterName() + "\"";
						jsonOutput += ",\"" + chapter.getStageCleanUp() + "\"";
						jsonOutput += ",\"" + chapter.getDocValidation() + "\"";
						jsonOutput += ",\"" + chapter.getStructuringVal() + "\"";
						jsonOutput += ",\"" + chapter.getPostVal() + "\"";
						jsonOutput += ",\"" + chapter.getPostConv() + "\"";
						if(chapter.getPreEditingCompletionDate() != null){
							String peCompletionDate = sdf.format(chapter.getPreEditingCompletionDate());
							jsonOutput += ",\"" + peCompletionDate + "\"";
						}else{
							jsonOutput += ",\"-\"";
						}
						jsonOutput += ",\"" + chapter.isWordExportMapStatus() + "\"";
						jsonOutput += ",\"" + chapter.getInDStyleMapStatus() + "\"";
						if(chapter.getReadyForUse() != null){
							if(chapter.getReadyForUse().equals("true")){
								jsonOutput += ",\"COMPLETED \"";
							}else{
								jsonOutput += ",\"FAILED \"";
							}
						}else{
							jsonOutput += ",\"-\"";
						}
						if(chapter.getModifiedOn() != null){
							String modifiedDate = sdf.format(chapter.getModifiedOn());
							jsonOutput += ",\"" + modifiedDate + "\"";
						}else{
							String createdDate = sdf.format(chapter.getCreatedOn());
							jsonOutput += ",\"" + createdDate + "\"";
						}
						jsonOutput += "]";
				}
			}
			response.getWriter().write("{\"data\":[" + jsonOutput + "]}");
		} catch (Exception e) {
			logger.error("Error while fetching the chapter details for job :: " + request.getParameter("jobId"));
		}
		return null;
	}
	
	/**
	 * Update chapter status
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/getUpdateChapter")
	public ModelAndView getChpater(ModelAndView model){
		return new ModelAndView("updateChapter", "stage", new Stage());
	}

	/**
	 * Update chapter status
	 * @param stage
	 * @return
	 */
	@PostMapping(value = "/updateChapter")
	public ModelAndView updateChapter(@ModelAttribute Stage stage, BindingResult result, Model model){
		try {
			if(result.hasErrors()){
				return new ModelAndView("updateChapter");
			}
			
			if(stage != null){
				MasterData masterDataObj = masterDataService.checkJobExists(stage.getJobId(), stage.getCustomerId());
				if(masterDataObj != null){
					ChapterDetails chapterDetail = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), stage.getChapterName());
					if(chapterDetail != null){
						Integer chapterUpdateStatus = chapterService.updateChapter(stage.isCleanUpStage(), stage.isDocVal(), stage.isStructVal(), stage.isPostVal(), stage.isPostConv(), stage.isMaestroCert(), chapterDetail.getId());
						ChapterDetails chapter = chapterService.isChapterDetailsExists(String.valueOf(masterDataObj.getId()), chapterDetail.getChapterName());
						if(chapterUpdateStatus > 0){
							model.addAttribute("chapter", chapter);
							model.addAttribute("responseMsg", "Updated successfully.");
						}else{
							model.addAttribute("responseMsg", "Chapter not updated.");
						}
						
					}else{
						model.addAttribute("responseMsg","Chapter not exists.");
					}
				}else{
					model.addAttribute("responseMsg","Job Id or Client Id not exists.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("updateChapter");
	}
	
	/**
	 * Add manuscripts
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/getManuscripts")
	public ModelAndView getMsPage(ModelAndView model){
		return new ModelAndView("add-manuscripts", "manuscripts", new AddManuscripts());
	}
	
	@PostMapping(value = "/addManuscripts")
	public ModelAndView addManuscripts(@ModelAttribute AddManuscripts manuscripts, BindingResult result, Model model){
		Integer noOfManuscripts = 0;
		try {
			if(result.hasErrors()){
				return new ModelAndView("updateChapter");
			}
			
			if(manuscripts != null){
				MasterData masterDataObj = masterDataService.checkJobExists(manuscripts.getJobId(), manuscripts.getCustomerId());
				if(masterDataObj != null){
					noOfManuscripts = masterDataObj.getNoOfManuscripts();
					
					/*if(noOfManuscripts.equals(Integer.valueOf(manuscripts.getManuscripts()))){*/
						int updateManuscripts = masterDataService.updateManuscripts(masterDataObj.getJobId(), masterDataObj.getClientId(), Integer.valueOf(manuscripts.getManuscripts()));
						if(updateManuscripts > 0){
							if(Integer.valueOf(manuscripts.getManuscripts()) > noOfManuscripts){
								int updatejobStatus = masterDataService.updateJobStatus(masterDataObj.getJobId(), masterDataObj.getClientId());
								if(updatejobStatus > 0){
									model.addAttribute("responseMsg","Manuscripts updated and job status changed to IN-PROGRESS.");
								}else{
									System.out.println("Failed to update the job status");
								}
								
							}else{
								model.addAttribute("responseMsg","Manuscripts updated");
							}
						}else{
							model.addAttribute("responseMsg","Manuscripts not updated.");
						}
					/*}else{
						model.addAttribute("responseMsg","Please increase or decrease the manuscripts.");
					}*/
				}else{
					model.addAttribute("responseMsg","Job Id or Client Id not exists.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("add-manuscripts");
		}
		return new ModelAndView("add-manuscripts");
	}
	
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
