package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class StringResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String response;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String comments;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String inDTemplateStatus;
	private HttpStatus status;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getInDTemplateStatus() {
		return inDTemplateStatus;
	}
	public void setInDTemplateStatus(String inDTemplateStatus) {
		this.inDTemplateStatus = inDTemplateStatus;
	}	
	
}