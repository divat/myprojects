package com.codemantra.maestro.response;

public class JobRequest {
	
	private String jobId;
	private String clientId;
	private String chapterName;
	private String inDTemplateStatus;
	private String comments;
	/*private String inDStyleStage;
	private String importStage;*/
	private String status;
	private String chapterDate;
	private String styleSheetModifiedDate;
	
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
	/*public String getInDStyleStage() {
		return inDStyleStage;
	}
	public void setInDStyleStage(String inDStyleStage) {
		this.inDStyleStage = inDStyleStage;
	}
	public String getImportStage() {
		return importStage;
	}
	public void setImportStage(String importStage) {
		this.importStage = importStage;
	}*/
	
	public String getStatus() {
		return status;
	}
	public String getInDTemplateStatus() {
		return inDTemplateStatus;
	}
	public void setInDTemplateStatus(String inDTemplateStatus) {
		this.inDTemplateStatus = inDTemplateStatus;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getChapterDate() {
		return chapterDate;
	}
	public void setChapterDate(String chapterDate) {
		this.chapterDate = chapterDate;
	}
	public String getStyleSheetModifiedDate() {
		return styleSheetModifiedDate;
	}
	public void setStyleSheetModifiedDate(String styleSheetModifiedDate) {
		this.styleSheetModifiedDate = styleSheetModifiedDate;
	}
}