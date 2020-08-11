package com.ReditClone.dto;

import java.io.Serializable;

public class NotificationEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Subject;
	private String recipient;
	private String body;

	public NotificationEmail() {

	}

	public NotificationEmail(String subject, String recipient, String body) {

		Subject = subject;
		this.recipient = recipient;
		this.body = body;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "NotificationEmail [Subject=" + Subject + ", recipient=" + recipient + ", body=" + body + "]";
	}

}
