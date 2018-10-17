package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class JobDetailsResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String serialNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String jobId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String path;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String graphicsPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String equationsPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String templateType;
	/*@JsonInclude(JsonInclude.Include.NON_NULL)*/
	private String templateName;
	private String noOfManuScripts;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private boolean isNewJob;
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getNoOfManuScripts() {
		return noOfManuScripts;
	}
	public void setNoOfManuScripts(String noOfManuScripts) {
		this.noOfManuScripts = noOfManuScripts;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public boolean isNewJob() {
		return isNewJob;
	}
	public void setNewJob(boolean isNewJob) {
		this.isNewJob = isNewJob;
	}
	public String getGraphicsPath() {
		return graphicsPath;
	}
	public void setGraphicsPath(String graphicsPath) {
		this.graphicsPath = graphicsPath;
	}
	public String getEquationsPath() {
		return equationsPath;
	}
	public void setEquationsPath(String equationsPath) {
		this.equationsPath = equationsPath;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}	
}