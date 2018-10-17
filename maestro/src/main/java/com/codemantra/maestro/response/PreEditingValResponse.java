package com.codemantra.maestro.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PreEditingValResponse {
	
	private String jobId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String value;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String chapterName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stageCleanUp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String docVal;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String structuringVal;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String postVal;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String postConv;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String copyEditPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String maestroMappingPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String graphicsPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String equationsPath;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String inDStyleMap;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String wdExportMap;
	
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
	public String getStageCleanUp() {
		return stageCleanUp;
	}
	public void setStageCleanUp(String stageCleanUp) {
		this.stageCleanUp = stageCleanUp;
	}
	public String getDocVal() {
		return docVal;
	}
	public void setDocVal(String docVal) {
		this.docVal = docVal;
	}
	public String getStructuringVal() {
		return structuringVal;
	}
	public void setStructuringVal(String structuringVal) {
		this.structuringVal = structuringVal;
	}
	public String getPostVal() {
		return postVal;
	}
	public void setPostVal(String postVal) {
		this.postVal = postVal;
	}
	public String getPostConv() {
		return postConv;
	}
	public void setPostConv(String postConv) {
		this.postConv = postConv;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMaestroMappingPath() {
		return maestroMappingPath;
	}
	public void setMaestroMappingPath(String maestroMappingPath) {
		this.maestroMappingPath = maestroMappingPath;
	}
	public String getCopyEditPath() {
		return copyEditPath;
	}
	public void setCopyEditPath(String copyEditPath) {
		this.copyEditPath = copyEditPath;
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
	public String getInDStyleMap() {
		return inDStyleMap;
	}
	public void setInDStyleMap(String inDStyleMap) {
		this.inDStyleMap = inDStyleMap;
	}
	public String getWdExportMap() {
		return wdExportMap;
	}
	public void setWdExportMap(String wdExportMap) {
		this.wdExportMap = wdExportMap;
	}
}