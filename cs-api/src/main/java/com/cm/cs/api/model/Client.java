package com.cm.cs.api.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "mast_cs_client")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Long id;
	
	@Column(name = "client_name")
	private String clientName;
	
	/*@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "client_division_mapping", joinColumns={@JoinColumn(name = "client_id")},
						inverseJoinColumns={@JoinColumn(name = "division_id")})
	private Set<Division> clientDivisionMapping;*/
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "created_by", referencedColumnName = "user_id")
	private Users createdBy;
	
	@OneToMany(mappedBy="client")
	private List<AutomationTools> tools;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/*public Set<Division> getClientDivisionMapping() {
		return clientDivisionMapping;
	}

	public void setClientDivisionMapping(Set<Division> clientDivisionMapping) {
		this.clientDivisionMapping = clientDivisionMapping;
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
	
}