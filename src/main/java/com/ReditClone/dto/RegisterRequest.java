package com.ReditClone.dto;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String username;
	private String password;
	
	public RegisterRequest() {

	
	}
	
	public RegisterRequest(String email, String username, String password) {
		
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "RegisterRequest [email=" + email + ", username=" + username + ", password=" + password + "]";
	}

	
	
	
}
