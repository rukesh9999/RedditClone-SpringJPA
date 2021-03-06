package com.ReditClone.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message ="UserName is Required")
	private String username;
	
	@NotBlank(message = "Password is Required")
	private String password;
	
	@Email
	@NotEmpty(message = "Email is Required")
	private String email;
	
	
	private Instant createdDate;
	private boolean enabled;
	
	
	
	
	public User() {
		
	}
	
	
	public User(Long userId, String user_name, String password, String email, Instant createdDate, boolean enabled) {
		this.userId = userId;
		this.username = user_name;
		this.password = password;
		this.email = email;
		this.createdDate = createdDate;
		this.enabled = enabled;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Instant getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", createdDate=" + createdDate + ", enabled=" + enabled + "]";
	}


			
}
