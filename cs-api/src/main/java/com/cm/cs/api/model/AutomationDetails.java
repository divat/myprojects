package com.cm.cs.api.model;

public class AutomationDetails {

	private String automationToolName;
	private Long divisionId;
	private String divisionName;
	private String clientId;
	private String clientName;
	private Double manualMetrics;
	private Integer manualPagesCount;
	private Double automationMetrics;
	private Integer automationPagesCount;
	private String createdOn;
	private String modifiedOn;
	
	public String getAutomationToolName() {
		return automationToolName;
	}
	public void setAutomationToolName(String automationToolName) {
		this.automationToolName = automationToolName;
	}
	public Long getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Double getManualMetrics() {
		return manualMetrics;
	}
	public void setManualMetrics(Double manualMetrics) {
		this.manualMetrics = manualMetrics;
	}
	public Integer getManualPagesCount() {
		return manualPagesCount;
	}
	public void setManualPagesCount(Integer manualPagesCount) {
		this.manualPagesCount = manualPagesCount;
	}
	public Double getAutomationMetrics() {
		return automationMetrics;
	}
	public void setAutomationMetrics(Double automationMetrics) {
		this.automationMetrics = automationMetrics;
	}
	public Integer getAutomationPagesCount() {
		return automationPagesCount;
	}
	public void setAutomationPagesCount(Integer automationPagesCount) {
		this.automationPagesCount = automationPagesCount;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
}