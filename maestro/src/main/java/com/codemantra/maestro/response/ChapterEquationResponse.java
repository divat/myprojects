package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ChapterEquationResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String jobId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String chapterName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String isEquationExists;
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
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getIsEquationExists() {
		return isEquationExists;
	}
	public void setIsEquationExists(String isEquationExists) {
		this.isEquationExists = isEquationExists;
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
}