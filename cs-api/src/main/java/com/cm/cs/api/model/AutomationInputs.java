package com.cm.cs.api.model;

public class AutomationInputs {

	private String automationName;
	private String division;
	private String client;
	private String manualMetrics;
	private String manualPages;
	private String automationMetrics;
	private String automationPages;
	private String toolDescription;
	
	public String getAutomationName() {
		return automationName;
	}
	public void setAutomationName(String automationName) {
		this.automationName = automationName;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getManualMetrics() {
		return manualMetrics;
	}
	public void setManualMetrics(String manualMetrics) {
		this.manualMetrics = manualMetrics;
	}
	public String getManualPages() {
		return manualPages;
	}
	public void setManualPages(String manualPages) {
		this.manualPages = manualPages;
	}
	public String getAutomationMetrics() {
		return automationMetrics;
	}
	public void setAutomationMetrics(String automationMetrics) {
		this.automationMetrics = automationMetrics;
	}
	public String getAutomationPages() {
		return automationPages;
	}
	public void setAutomationPages(String automationPages) {
		this.automationPages = automationPages;
	}
	public String getToolDescription() {
		return toolDescription;
	}
	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}
}