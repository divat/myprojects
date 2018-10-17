package com.codemantra.maestro.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MailRequestWrapper {

	private String groupName;
	private String subject;
	private String message;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
/*	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}*/
	
	
}
