package com.ReditClone.dto;

import java.io.Serializable;

import lombok.Builder;

@Builder
public class SubredditDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subredditId;	
	private String name;
	private String description;
	private String createdDate;
	private String user;
	
	public SubredditDto(Long subredditId, String name, String description, String createdDate,
			String user) {

		this.subredditId = subredditId;
		this.name = name;
		this.description = description;
		this.createdDate = createdDate;
		this.user = user;
	}

    
	public SubredditDto() {
		
	}


	public Long getSubredditId() {
		return subredditId;
	}


	public void setSubredditId(Long subredditId) {
		this.subredditId = subredditId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "SubredditDto [subredditId=" + subredditId + ", name=" + name + ", description=" + description
				+ ", createdDate=" + createdDate + ", user=" + user + "]";
	}

	
	
	
}
