package com.ReditClone.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


public class RefreshTokenRequest implements Serializable{

	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@NotBlank
		private String refreshToken;
		private String username;
		
		public RefreshTokenRequest() {
			
		}
		
		public RefreshTokenRequest(@NotBlank String refreshToken, String userName) {
			
			this.refreshToken = refreshToken;
			this.username = userName;
		}

		public String getRefreshToken() {
			return refreshToken;
		}

		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public String toString() {
			return "RefreshTokenRequest [refreshToken=" + refreshToken + ", username=" + username + "]";
		}
		
		
				
		
	
}
