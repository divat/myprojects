package com.cm.cs.api.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String divisionName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String projectName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String automationName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String developedBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer noOfJobs;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HttpStatus status;
	
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAutomationName() {
		return automationName;
	}
	public void setAutomationName(String automationName) {
		this.automationName = automationName;
	}
	public String getDevelopedBy() {
		return developedBy;
	}
	public void setDevelopedBy(String developedBy) {
		this.developedBy = developedBy;
	}
	public Integer getNoOfJobs() {
		return noOfJobs;
	}
	public void setNoOfJobs(Integer noOfJobs) {
		this.noOfJobs = noOfJobs;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}