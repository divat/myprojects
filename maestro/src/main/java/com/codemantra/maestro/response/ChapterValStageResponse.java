package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ChapterValStageResponse {

	private String jobId;
	private String clientId;
	private String chapterName;
	/*private String inDesignStyleChecking;
	private String inDesignImportMap;*/
	private String templateStatus;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String comments;
	private HttpStatus status;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	/*public String getInDesignStyleChecking() {
		return inDesignStyleChecking;
	}
	public void setInDesignStyleChecking(String inDesignStyleChecking) {
		this.inDesignStyleChecking = inDesignStyleChecking;
	}
	public String getInDesignImportMap() {
		return inDesignImportMap;
	}
	public void setInDesignImportMap(String inDesignImportMap) {
		this.inDesignImportMap = inDesignImportMap;
	}*/
	
	public HttpStatus getStatus() {
		return status;
	}
	public String getTemplateStatus() {
		return templateStatus;
	}
	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

}