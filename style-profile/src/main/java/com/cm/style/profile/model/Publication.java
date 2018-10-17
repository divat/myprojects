package com.cm.style.profile.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Publication entity
 * @author DHIVAKART
 *
 */
@Entity
@Table(name = "publication_details")
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_id_seq")
	@SequenceGenerator(name = "publication_id_seq", sequenceName = "publication_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "publication_id")
	private Long id;
	
	@Column(name = "publication_name", nullable = false)
	private String publicationName;
	
	@ManyToOne
	@JoinTable(name = "publisher_publication_mapping", 
			joinColumns = { @JoinColumn(name = "publication_id")},
			inverseJoinColumns = { @JoinColumn(name = "publisher_id")})
	/*@JoinColumn(name="publisher_id", nullable=false)
	/*@JoinColumn(name="publisher_id")*/
	private Publisher publisher;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publication")
	private List<Styles> style = new ArrayList<Styles>();
	
	/*@OneToMany(mappedBy = "publication")
	private Styles style;*/
	
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

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Styles> getStyle() {
		return style;
	}

	public void setStyle(List<Styles> style) {
		this.style = style;
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

	/*public Styles getStyle() {
		return style;
	}

	public void setStyle(Styles style) {
		this.style = style;
	}*/
	
}