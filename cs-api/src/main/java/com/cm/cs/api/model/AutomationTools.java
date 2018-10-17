package com.cm.cs.api.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author DHIVAKART
 *
 */
@Entity
@Table(name = "mast_cs_automation_tools")
public class AutomationTools {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "automation_tool_id")
	private Long id;
	
	/*@Column(name = "client_name")
	private String clientName;*/
	
	@Column(name = "automation_tool_name")
	private String automationName;
	
	@ManyToOne
	@JoinTable(name = "mast_division_tools_mapping",
			joinColumns = { @JoinColumn(name = "automation_tool_id")},
			inverseJoinColumns = { @JoinColumn(name = "division_id")})
	private Division division;
	
	@ManyToOne
	@JoinTable(name = "mast_client_tools_mapping",
			joinColumns = { @JoinColumn(name = "automation_tool_id")},
			inverseJoinColumns = { @JoinColumn(name = "client_id")})
	private Client client;
	
	@Column(name = "manual_pages_count")
	private Integer manualNoOfPages;
	
	@Column(name = "manual_metrics")
	private double manualMetrics;
	
	@Column(name = "automation_pages_count")
	private Integer automationNoOfPages;
	
	@Column(name = "automation_metrics")
	private double automationMetrics;
	
	@OneToMany(mappedBy="automationToolDetails")
	private List<AutomationMetrics> metricsList;
	
	@Column(name = "developed_by")
	private String createdBy;
	
	@Column(name = "release_date")
	private Date releaseDate;
	
	@Column(name = "created_on")
	private java.sql.Timestamp createdOn;
	
	@Column(name = "modified_on")
	private java.sql.Timestamp modifiedOn;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@Column(name = "tool_description")
	private String toolDescription;
	
	/*@OneToMany(fetch=FetchType.EAGER, mappedBy="automationName")
	private List<AutomationMetrics> automationInputs;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	/*public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}*/

	public String getAutomationName() {
		return automationName;
	}

	public void setAutomationName(String automationName) {
		this.automationName = automationName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public java.sql.Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(java.sql.Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/*public List<AutomationMetrics> getAutomationInputs() {
		return automationInputs;
	}

	public void setAutomationInputs(List<AutomationMetrics> automationInputs) {
		this.automationInputs = automationInputs;
	}*/

	public Integer getManualNoOfPages() {
		return manualNoOfPages;
	}

	public void setManualNoOfPages(Integer manualNoOfPages) {
		this.manualNoOfPages = manualNoOfPages;
	}

	public double getManualMetrics() {
		return manualMetrics;
	}

	public void setManualMetrics(double manualMetrics) {
		this.manualMetrics = manualMetrics;
	}

	public Integer getAutomationNoOfPages() {
		return automationNoOfPages;
	}

	public void setAutomationNoOfPages(Integer automationNoOfPages) {
		this.automationNoOfPages = automationNoOfPages;
	}

	public double getAutomationMetrics() {
		return automationMetrics;
	}

	public void setAutomationMetrics(double automationMetrics) {
		this.automationMetrics = automationMetrics;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<AutomationMetrics> getMetricsList() {
		return metricsList;
	}

	public void setMetricsList(List<AutomationMetrics> metricsList) {
		this.metricsList = metricsList;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getToolDescription() {
		return toolDescription;
	}

	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}

	
}