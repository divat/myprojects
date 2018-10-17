package com.cm.cs.api.model;

public class ToolMetrics {

	private String automationToolId;
	private String automationName;
	private String clientName;
	private String divisionName;
	private Integer manualPagesCount;
	private double manualMetrics;
	private Integer automationPagesCount;
	private double automationMetrics;
	private String createdOn;
	private Integer prodNoOfPages;
	private Double projectedManualMetrics;
	private Double projectedAutomationMetrics;
	private String description;
	
	public String getAutomationName() {
		return automationName;
	}
	public void setAutomationName(String automationName) {
		this.automationName = automationName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public Integer getManualPagesCount() {
		return manualPagesCount;
	}
	public void setManualPagesCount(Integer manualPagesCount) {
		this.manualPagesCount = manualPagesCount;
	}
	public double getManualMetrics() {
		return manualMetrics;
	}
	public void setManualMetrics(double manualMetrics) {
		this.manualMetrics = manualMetrics;
	}
	public Integer getAutomationPagesCount() {
		return automationPagesCount;
	}
	public void setAutomationPagesCount(Integer automationPagesCount) {
		this.automationPagesCount = automationPagesCount;
	}
	public double getAutomationMetrics() {
		return automationMetrics;
	}
	public void setAutomationMetrics(double automationMetrics) {
		this.automationMetrics = automationMetrics;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getProdNoOfPages() {
		return prodNoOfPages;
	}
	public void setProdNoOfPages(Integer prodNoOfPages) {
		this.prodNoOfPages = prodNoOfPages;
	}
	public String getAutomationToolId() {
		return automationToolId;
	}
	public void setAutomationToolId(String automationToolId) {
		this.automationToolId = automationToolId;
	}
	public Double getProjectedManualMetrics() {
		return projectedManualMetrics;
	}
	public void setProjectedManualMetrics(Double projectedManualMetrics) {
		this.projectedManualMetrics = projectedManualMetrics;
	}
	public Double getProjectedAutomationMetrics() {
		return projectedAutomationMetrics;
	}
	public void setProjectedAutomationMetrics(Double projectedAutomationMetrics) {
		this.projectedAutomationMetrics = projectedAutomationMetrics;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}