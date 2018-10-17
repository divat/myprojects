package com.codemantra.maestro.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mast_chapter_details")
public class ChapterDetails {

	@Id
	@SequenceGenerator(name = "chapters_seq_id", sequenceName = "mast_chapter_seq", allocationSize=50)
	@GeneratedValue(generator = "chapters_seq_id")
	private Long id;
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;*/
	
	@Column(name = "chapter_name")
	private String chapterName;
	
	@Column(name = "stage_clean_up")
	private boolean stageCleanUp;
	
	/*@Column(name = "stage_pre_edit")
	private boolean preEditStage;*/
	
	@Column(name = "stage_doc_val")
	private boolean docValidation;
	
	@Column(name = "stage_structuring_val")
	private boolean structuringVal;
	
	@Column(name = "stage_post_val")
	private boolean postVal;
	
	@Column(name = "stage_post_conv")
	private boolean postConv;
	
	/*@Column(name = "stage_ws_against_id_style_checking")
	private boolean stageWsStyleChecking;
	
	@Column(name = "stage_ws_against_id_import_map")
	private boolean stageWsInDImportMap;*/
	
	@Column(name = "inD_style_import_map_status", columnDefinition = "boolean default false", nullable = false)
	private boolean inDStyleMapStatus;
	
	@Column(name = "indgn_template_comments")
	private String inDStyleMapComments;
	
	@Column(name = "pre_editing_completion_date")
	private Timestamp preEditingCompletionDate;
	
	@Column(name = "in_design_style_completion_date")
	private Timestamp inDStyleCompletionDate;
	
	/*@Column(name = "stage_ws_against_word_import_map")
	private boolean stageWsWordImportMap;*/
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "metadata_job_id")
	private MasterData metadataJobId;
	
	@JsonIgnore
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@JsonIgnore
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@JsonIgnore
	@Column(name="status")
	private String readyForUse;
	
	@Column(name = "is_mEquation_Exist", nullable=true)
	private boolean mEquation;
	
	@Column(name = "chapter_modified_date")
	private Timestamp chapterModifiedDate;
	
	@Column(name = "style_sheet_modified_date", nullable = true)
	private Timestamp styleSheetModifiedDate;
	
	@Column(name = "stage_word_export_map", nullable = true)
	private boolean wordExportMapStatus;
	
	/*@OneToOne
	@JoinColumn(name = "metadata_client_id")
	private MasterData metadataClientId;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getStageCleanUp() {
		return stageCleanUp;
	}

	public void setStageCleanUp(boolean stageCleanUp) {
		this.stageCleanUp = stageCleanUp;
	}

	/*public boolean getPreEditStage() {
		return preEditStage;
	}

	public void setPreEditStage(boolean preEditStage) {
		this.preEditStage = preEditStage;
	}*/

	public boolean getDocValidation() {
		return docValidation;
	}

	public void setDocValidation(boolean docValidation) {
		this.docValidation = docValidation;
	}

	public boolean getStructuringVal() {
		return structuringVal;
	}

	public void setStructuringVal(boolean structuringVal) {
		this.structuringVal = structuringVal;
	}

	public boolean getPostVal() {
		return postVal;
	}

	public void setPostVal(boolean postVal) {
		this.postVal = postVal;
	}

	public boolean getPostConv() {
		return postConv;
	}

	public void setPostConv(boolean postConv) {
		this.postConv = postConv;
	}

	/*public boolean getStageWsStyleChecking() {
		return stageWsStyleChecking;
	}

	public void setStageWsStyleChecking(boolean stageWsStyleChecking) {
		this.stageWsStyleChecking = stageWsStyleChecking;
	}

	public boolean getStageWsInDImportMap() {
		return stageWsInDImportMap;
	}

	public void setStageWsInDImportMap(boolean stageWsInDImportMap) {
		this.stageWsInDImportMap = stageWsInDImportMap;
	}*/

	/*public boolean getStageWsWordImportMap() {
		return stageWsWordImportMap;
	}

	public void setStageWsWordImportMap(boolean stageWsWordImportMap) {
		this.stageWsWordImportMap = stageWsWordImportMap;
	}*/

	public MasterData getMetadataJobId() {
		return metadataJobId;
	}

	public void setMetadataJobId(MasterData metadataJobId) {
		this.metadataJobId = metadataJobId;
	}

	/*public MasterData getMetadataClientId() {
		return metadataClientId;
	}

	public void setMetadataClientId(MasterData metadataClientId) {
		this.metadataClientId = metadataClientId;
	}*/

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
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

	public String getReadyForUse() {
		return readyForUse;
	}

	public void setReadyForUse(String readyForUse) {
		this.readyForUse = readyForUse;
	}

	public boolean getInDStyleMapStatus() {
		return inDStyleMapStatus;
	}

	public void setInDStyleMapStatus(boolean inDStyleMapStatus) {
		this.inDStyleMapStatus = inDStyleMapStatus;
	}

	public String getInDStyleMapComments() {
		return inDStyleMapComments;
	}

	public void setInDStyleMapComments(String inDStyleMapComments) {
		this.inDStyleMapComments = inDStyleMapComments;
	}

	public Timestamp getPreEditingCompletionDate() {
		return preEditingCompletionDate;
	}

	public void setPreEditingCompletionDate(Timestamp preEditingCompletionDate) {
		this.preEditingCompletionDate = preEditingCompletionDate;
	}

	public Timestamp getInDStyleCompletionDate() {
		return inDStyleCompletionDate;
	}

	public void setInDStyleCompletionDate(Timestamp inDStyleCompletionDate) {
		this.inDStyleCompletionDate = inDStyleCompletionDate;
	}

	public boolean ismEquation() {
		return mEquation;
	}

	public void setmEquation(boolean mEquation) {
		this.mEquation = mEquation;
	}

	public Timestamp getChapterModifiedDate() {
		return chapterModifiedDate;
	}

	public void setChapterModifiedDate(Timestamp chapterModifiedDate) {
		this.chapterModifiedDate = chapterModifiedDate;
	}

	public Timestamp getStyleSheetModifiedDate() {
		return styleSheetModifiedDate;
	}

	public void setStyleSheetModifiedDate(Timestamp styleSheetModifiedDate) {
		this.styleSheetModifiedDate = styleSheetModifiedDate;
	}

	public boolean isWordExportMapStatus() {
		return wordExportMapStatus;
	}

	public void setWordExportMapStatus(boolean wordExportMapStatus) {
		this.wordExportMapStatus = wordExportMapStatus;
	}
	
	
	/*public String getStyleCheckingComments() {
		return styleCheckingComments;
	}

	public void setStyleCheckingComments(String styleCheckingComments) {
		this.styleCheckingComments = styleCheckingComments;
	}

	public String getImportMapComments() {
		return importMapComments;
	}

	public void setImportMapComments(String importMapComments) {
		this.importMapComments = importMapComments;
	}*/
	
}