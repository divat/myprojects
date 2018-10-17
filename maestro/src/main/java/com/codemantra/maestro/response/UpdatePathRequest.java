package com.codemantra.maestro.response;

/**
 * Request wrapper to get the 
 * path details for job
 * @author DHIVAKART
 *
 */
public class UpdatePathRequest {

	private String jobId;
	private String clientId;
	private String copyEditPath;
	private String equationsPath;
	private String graphicsPath;
	private String templatePath;
	private String mappingPath;
	private String styleSheetPath;
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
	public String getCopyEditPath() {
		return copyEditPath;
	}
	public void setCopyEditPath(String copyEditPath) {
		this.copyEditPath = copyEditPath;
	}
	public String getEquationsPath() {
		return equationsPath;
	}
	public void setEquationsPath(String equationsPath) {
		this.equationsPath = equationsPath;
	}
	public String getGraphicsPath() {
		return graphicsPath;
	}
	public void setGraphicsPath(String graphicsPath) {
		this.graphicsPath = graphicsPath;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getMappingPath() {
		return mappingPath;
	}
	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}
	public String getStyleSheetPath() {
		return styleSheetPath;
	}
	public void setStyleSheetPath(String styleSheetPath) {
		this.styleSheetPath = styleSheetPath;
	}
	
}