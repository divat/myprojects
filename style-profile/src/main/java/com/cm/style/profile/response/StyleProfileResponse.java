package com.cm.style.profile.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class StyleProfileResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String publisherName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String publicationName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String client;
	/*@JsonInclude(JsonInclude.Include.NON_NULL)
	private String uID;*/
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String style;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HttpStatus status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String isStyleExecuted;
	
	public StyleProfileResponse(){
		
	}
	
	public StyleProfileResponse(HttpStatus status, String message){
		this.status = status;
		this.message = message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	/*public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}*/

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getIsStyleExecuted() {
		return isStyleExecuted;
	}

	public void setIsStyleExecuted(String isStyleExecuted) {
		this.isStyleExecuted = isStyleExecuted;
	}
	
}
