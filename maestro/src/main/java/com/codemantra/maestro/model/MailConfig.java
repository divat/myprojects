package com.codemantra.maestro.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mast_mail_config")
public class MailConfig {

	@Id
	@SequenceGenerator(name = "mail_config_id", sequenceName = "mail_config_seq")
	@GeneratedValue(generator = "mail_config_id")
	private Long id;
	
	@Column(name= "group_name")
	private String groupName;
	
	@Column(name = "user_email_ids")
	private String userEmailIds;
	
	@JsonIgnore
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@JsonIgnore
	@Column(name = "modified_on")
	private Timestamp modifiedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserEmailIds() {
		return userEmailIds;
	}

	public void setUserEmailIds(String userEmailIds) {
		this.userEmailIds = userEmailIds;
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
	
}