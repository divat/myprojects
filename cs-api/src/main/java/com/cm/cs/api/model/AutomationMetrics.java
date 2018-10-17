package com.cm.cs.api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mast_automation_metrics")
public class AutomationMetrics {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "job_Id")
	private String jobId;
	
	@Column(name = "page_or_image_count")
	private Integer pageOrImageCount;
	
	@Column(name = "job_type")
	private String jobType;
	
	@ManyToOne
	@JoinTable(name = "mast_automation_tools_input_mapping",
			joinColumns = { @JoinColumn(name = "id")},
			inverseJoinColumns = { @JoinColumn(name = "automation_tool_id")})
	private AutomationTools automationToolDetails;
	
	@Column(name = "job_created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

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

	public AutomationTools getAutomationToolDetails() {
		return automationToolDetails;
	}

	public void setAutomationToolDetails(AutomationTools automationToolDetails) {
		this.automationToolDetails = automationToolDetails;
	}

	public Integer getPageOrImageCount() {
		return pageOrImageCount;
	}

	public void setPageOrImageCount(Integer pageOrImageCount) {
		this.pageOrImageCount = pageOrImageCount;
	}	
	
	
}