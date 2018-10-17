package com.codemantra.maestro.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.codemantra.maestro.model.ChapterDetails;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ChapterUpdateResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String jobId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer chaptersMissing;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ChapterDetails> chaptersList;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
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
	public Integer getChaptersMissing() {
		return chaptersMissing;
	}
	public void setChaptersMissing(Integer chaptersMissing) {
		this.chaptersMissing = chaptersMissing;
	}
	public List<ChapterDetails> getChaptersList() {
		return chaptersList;
	}
	public void setChaptersList(List<ChapterDetails> list) {
		this.chaptersList = list;
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