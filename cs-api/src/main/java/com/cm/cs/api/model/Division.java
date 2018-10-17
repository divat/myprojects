package com.cm.cs.api.model;


import java.sql.Timestamp;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author DHIVAKART
 *
 */
@Entity
@Table(name = "mast_cs_division")
public class Division {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "division_id")
	private Long id;
	
	@Column(name = "division_name")
	private String divisionName;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "created_by", referencedColumnName = "user_id")
	private Users createdBy;
	
	/*@ManyToMany(mappedBy="clientDivisionMapping")
	private Set<Client> client;*/
	
	@OneToMany(mappedBy="division")
	private List<AutomationTools> tools;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public List<AutomationTools> getTools() {
		return tools;
	}

	public void setTools(List<AutomationTools> tools) {
		this.tools = tools;
	}

	/*public Set<Client> getClient() {
		return client;
	}

	public void setClient(Set<Client> client) {
		this.client = client;
	}*/

}