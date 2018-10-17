package com.cm.cs.api.model;

public class AutomationJobs {

	private String automationName;
	private String jobId;
	private String jobType;
	private String jobCreatedOn;
	private Integer pageOrImageCount;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobCreatedOn() {
		return jobCreatedOn;
	}
	public void setJobCreatedOn(String jobCreatedOn) {
		this.jobCreatedOn = jobCreatedOn;
	}
	public Integer getPageOrImageCount() {
		return pageOrImageCount;
	}
	public void setPageOrImageCount(Integer pageOrImageCount) {
		this.pageOrImageCount = pageOrImageCount;
	}
	public String getAutomationName() {
		return automationName;
	}
	public void setAutomationName(String automationName) {
		this.automationName = automationName;
	}
	
}