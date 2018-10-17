package com.cm.cs.api.model;

public class ClientDivisionReport {

	private String divisionName;
	private String clientName;
	private Integer noOfPages;
	private Double projectedManuamMetrics;
	private Double projectedAutomationMetrics;
	
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}
	public Double getProjectedManuamMetrics() {
		return projectedManuamMetrics;
	}
	public void setProjectedManuamMetrics(Double projectedManuamMetrics) {
		this.projectedManuamMetrics = projectedManuamMetrics;
	}
	public Double getProjectedAutomationMetrics() {
		return projectedAutomationMetrics;
	}
	public void setProjectedAutomationMetrics(Double projectedAutomationMetrics) {
		this.projectedAutomationMetrics = projectedAutomationMetrics;
	}
	
}