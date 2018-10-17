/*package com.cm.style.profile.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

*//**
 * Role entity
 * @author DHIVAKART
 *
 *//*
@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_id_seq")
	@SequenceGenerator(name = "role_id_seq", sequenceName = "role_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "role_id")
	private Long id;
	
	@Column(name = "role_name", nullable = false, length=15)
	private String roleName;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "created_by", nullable = false)
	private Integer createdBy;
	
	@Column(name = "modified_by", nullable = false)
	private Integer modifiedBy;
	
	@Column(name = "created_on", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "modified_on", nullable = false)
	private Timestamp modifiedOn;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;
	
	@ManyToOne
	@JoinTable(name = "user_role_mapping",
	   joinColumns = { @JoinColumn(name = "role_id")},
	   inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Users user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
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
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
}*/