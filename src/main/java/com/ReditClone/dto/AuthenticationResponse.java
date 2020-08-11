package com.ReditClone.dto;

import java.io.Serializable;
import java.time.Instant;

public class AuthenticationResponse implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authenticationToken;
    private String username;
    private Instant expiresAt;
    private String refreshToken;
    
    
	public AuthenticationResponse() {

	}
	
	
   	public AuthenticationResponse(String authenticationToken, String username, Instant expiresAt, String refreshToken) {
		
		this.authenticationToken = authenticationToken;
		this.username = username;
		this.expiresAt = expiresAt;
		this.refreshToken = refreshToken;
	}


	public String getAuthenticationToken() {
		return authenticationToken;
	}
   	
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}


	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [authenticationToken=" + authenticationToken + ", username=" + username + "]";
	}
    
    
}
