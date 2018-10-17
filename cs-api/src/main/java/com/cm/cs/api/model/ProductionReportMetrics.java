package com.cm.cs.api.model;

public class ProductionReportMetrics {

	private String clientName;
	private String divisionName;
	private String automationToolName;
	private String createdOn;
	private Integer productionPageCount;
	private double projectedManualMetrics;
	private double projectedAutomationMetrics;
	
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
	public String getAutomationToolName() {
		return automationToolName;
	}
	public void setAutomationToolName(String automationToolName) {
		this.automationToolName = automationToolName;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getProductionPageCount() {
		return productionPageCount;
	}
	public void setProductionPageCount(Integer productionPageCount) {
		this.productionPageCount = productionPageCount;
	}
	public double getProjectedManualMetrics() {
		return projectedManualMetrics;
	}
	public void setProjectedManualMetrics(double projectedManualMetrics) {
		this.projectedManualMetrics = projectedManualMetrics;
	}
	public double getProjectedAutomationMetrics() {
		return projectedAutomationMetrics;
	}
	public void setProjectedAutomationMetrics(double projectedAutomationMetrics) {
		this.projectedAutomationMetrics = projectedAutomationMetrics;
	}
	
	
}
