package com.ReditClone.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RefreshToken  implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String token;
	private Instant createdDate;
	
	public RefreshToken() {

	}
	
	public RefreshToken(long id, String token, Instant createdDate) {
		
		this.id = id;
		this.token = token;
		this.createdDate = createdDate;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Instant getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", token=" + token + ", createdDate=" + createdDate + "]";
	}
	
	

}
