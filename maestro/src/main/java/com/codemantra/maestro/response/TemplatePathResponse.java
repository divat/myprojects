package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TemplatePathResponse {

	private String jobId;
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String templateName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String templatePath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String styleSheetPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String maestroMappingPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String response;
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
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getStyleSheetPath() {
		return styleSheetPath;
	}
	public void setStyleSheetPath(String styleSheetPath) {
		this.styleSheetPath = styleSheetPath;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMaestroMappingPath() {
		return maestroMappingPath;
	}
	public void setMaestroMappingPath(String maestroMappingPath) {
		this.maestroMappingPath = maestroMappingPath;
	}
	
}
