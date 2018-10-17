package com.cm.style.profile.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Publisher entity
 * @author DHIVAKART
 *
 */
@Entity
@Table(name = "publisher_details")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="publisher_seq_id")
	@SequenceGenerator(name = "publisher_seq_id", sequenceName = "publisher_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "publisher_id")
	private Long id;
	
	@Column(name = "publisher_name", nullable=false)
	private String publisherName;
	
	/*@Column(name = "publication_name", nullable=false)
	private String publicationName;*/
	
	/*@OneToMany(mappedBy = "publisher")
	private List<Styles> style = new ArrayList<Styles>();*/
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="publisher")
	private Set<Publication> publication = new HashSet<Publication>();
	
	@Column(name = "is_active", columnDefinition = "boolean default false", nullable = false)
	private boolean isActive;
	
	@Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false)
	private boolean isDeleted;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by", referencedColumnName = "user_id")
	private Users createdBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/*public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}*/
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
	public Users getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
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
	/*public Set<Publication> getPublication() {
		return publication;
	}
	public void setPublication(Set<Publication> publication) {
		this.publication = publication;
	}*/
	
}