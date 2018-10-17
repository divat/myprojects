package com.cm.style.profile.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Style history
 * @author DHIVAKART
 *
 */
@Entity
@Table(name = "styles_history")
public class StylesHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="styles_history_id_seq")
	@SequenceGenerator(name="styles_history_id_seq", sequenceName = "styles_history_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "style_id", nullable = false)
	private Long styleId;
	
	@Column(name = "style_name", nullable = false)
	private String styles;
	
	/*@Column(name = "style_uid", nullable = false)
	private String uID;*/
	
	@ManyToOne
	/*@JoinTable(name = "publication_style_mapping",
	   joinColumns = { @JoinColumn(name = "style_id")}, 
	   inverseJoinColumns = { @JoinColumn(name = "publication_id")})*/
	@JoinColumn(name = "publication_id", nullable=false)
	private Publication publication;
	
	/*@ManyToOne
	@JoinTable(name = "publisher_styles_mapping",
	   joinColumns = { @JoinColumn(name = "style_id")}, 
	   inverseJoinColumns = { @JoinColumn(name = "publication_id")})
	private Publisher publisher;*/
	
	/*@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by", referencedColumnName = "user_id")*/
	@Column(name = "created_by")
	private String createdBy;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "modified_by", referencedColumnName = "user_id")
	private Users modifiedBy;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
	private Client client;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "is_updated_style", nullable = false)
	private boolean isUpdatedStyle;

	@Column(name = "style_moved_on")
	private Timestamp updatedStyleDate;
	
	@Column(name = "is_style_profile_success")
	private boolean styleProfileExecuted;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	/*public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}*/
	
	public String getCreatedBy() {
		return createdBy;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Users getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Users modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/*public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}*/

	public boolean isUpdatedStyle() {
		return isUpdatedStyle;
	}

	public void setUpdatedStyle(boolean isUpdatedStyle) {
		this.isUpdatedStyle = isUpdatedStyle;
	}

	public Timestamp getUpdatedStyleDate() {
		return updatedStyleDate;
	}

	public void setUpdatedStyleDate(Timestamp updatedStyleDate) {
		this.updatedStyleDate = updatedStyleDate;
	}

	public Long getStyleId() {
		return styleId;
	}

	public void setStyleId(Long styleId) {
		this.styleId = styleId;
	}

	public boolean isStyleProfileExecuted() {
		return styleProfileExecuted;
	}

	public void setStyleProfileExecuted(boolean styleProfileExecuted) {
		this.styleProfileExecuted = styleProfileExecuted;
	}
	
	
}