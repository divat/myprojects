package com.codemantra.maestro.model;

public class Stage {

	private String customerId;
	
	private String jobId;
	
	private String chapterName;
	
	private boolean cleanUpStage;
	
	private boolean docVal;
	
	private boolean structVal;
	
	private boolean postVal;
	
	private boolean postConv;
	
	private boolean maestroCert;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public boolean isCleanUpStage() {
		return cleanUpStage;
	}

	public void setCleanUpStage(boolean cleanUpStage) {
		this.cleanUpStage = cleanUpStage;
	}

	public boolean isDocVal() {
		return docVal;
	}

	public void setDocVal(boolean docVal) {
		this.docVal = docVal;
	}

	public boolean isStructVal() {
		return structVal;
	}

	public void setStructVal(boolean structVal) {
		this.structVal = structVal;
	}

	public boolean isPostVal() {
		return postVal;
	}

	public void setPostVal(boolean postVal) {
		this.postVal = postVal;
	}

	public boolean isPostConv() {
		return postConv;
	}

	public void setPostConv(boolean postConv) {
		this.postConv = postConv;
	}

	public boolean isMaestroCert() {
		return maestroCert;
	}

	public void setMaestroCert(boolean maestroCert) {
		this.maestroCert = maestroCert;
	}

}