package com.codemantra.maestro.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mast_metadata")
public class MasterData {

	@Id
	@SequenceGenerator(name = "mast_metadata_id", sequenceName = "mast_job_metadata_seq")
	@GeneratedValue(generator = "mast_metadata_id")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "clientId")
	private String clientId;
	
	@Column(name = "job_id")
	private String jobId;
	
	@Column(name = "stage1_copy_edit_path")
	private String stage1CopyEdit;
	
	@Column(name = "stage2_graphics_path")
	private String stage2Graphics;
	
	@Column(name = "stage3_equations_path")
	private String stage3Equations;
	
	@Column(name = "stage4_template_name")
	private String stage4TemplateName;
	
	@Column(name = "stage5_template_path")
	private String stage5TemplatePath;
	
	@Column(name = "stage6_maestro_map_path")
	private String stage6MaestroMappingPath;

	@Column(name = "stage7_standard_generic_style_sheet")
	private String Stage7StandardGenericStyleSheet;
	
	@Column(name = "no_of_manuscripts")
	private Integer noOfManuscripts;
	
	@Column(name = "job_status")
	private String jobStatus;
	
	@Column(name = "is_new_job")
	private boolean isNewJob;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "job_isbn")
	private String jobIsbn;
	
	@Column(name = "job_serial_no")
	private String sId;
	
	@Column(name = "template_type")
	private String templateType;
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	@Column(name = "modified_on")
	public Timestamp getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	@OneToOne
	@JoinColumn(name = "file_metadata_id")
	private FileMetadata fileMetadata;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getStage1CopyEdit() {
		return stage1CopyEdit;
	}
	public void setStage1CopyEdit(String stage1CopyEdit) {
		this.stage1CopyEdit = stage1CopyEdit;
	}
	public String getStage2Graphics() {
		return stage2Graphics;
	}
	public void setStage2Graphics(String stage2Graphics) {
		this.stage2Graphics = stage2Graphics;
	}
	public String getStage3Equations() {
		return stage3Equations;
	}
	public void setStage3Equations(String stage3Equations) {
		this.stage3Equations = stage3Equations;
	}
	public String getStage4TemplateName() {
		return stage4TemplateName;
	}
	public void setStage4TemplateName(String stage4TemplateName) {
		this.stage4TemplateName = stage4TemplateName;
	}
	public String getStage5TemplatePath() {
		return stage5TemplatePath;
	}
	public void setStage5TemplatePath(String stage5TemplatePath) {
		this.stage5TemplatePath = stage5TemplatePath;
	}
	public String getStage6MaestroMappingPath() {
		return stage6MaestroMappingPath;
	}
	public void setStage6MaestroMappingPath(String stage6MaestroMappingPath) {
		this.stage6MaestroMappingPath = stage6MaestroMappingPath;
	}
	public FileMetadata getFileMetadata() {
		return fileMetadata;
	}
	public void setFileMetadata(FileMetadata fileMetadata) {
		this.fileMetadata = fileMetadata;
	}
	public String getStage7StandardGenericStyleSheet() {
		return Stage7StandardGenericStyleSheet;
	}
	public void setStage7StandardGenericStyleSheet(String stage7StandardGenericStyleSheet) {
		Stage7StandardGenericStyleSheet = stage7StandardGenericStyleSheet;
	}
	public Integer getNoOfManuscripts() {
		return noOfManuscripts;
	}
	public void setNoOfManuscripts(Integer noOfManuscripts) {
		this.noOfManuscripts = noOfManuscripts;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isNewJob() {
		return isNewJob;
	}

	public void setNewJob(boolean isNewJob) {
		this.isNewJob = isNewJob;
	}

	public String getJobIsbn() {
		return jobIsbn;
	}

	public void setJobIsbn(String jobIsbn) {
		this.jobIsbn = jobIsbn;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

}