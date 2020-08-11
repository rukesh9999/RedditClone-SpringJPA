package com.ReditClone.dto;

import java.io.Serializable;

public class PostRequest implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long postId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;
	
    public PostRequest() {
	
	}
	
	public PostRequest(Long postId, String subredditName, String postName, String url, String description) {
		
		this.postId = postId;
		this.subredditName = subredditName;
		this.postName = postName;
		this.url = url;
		this.description = description;
	}
	
	
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getSubredditName() {
		return subredditName;
	}
	public void setSubredditName(String subredditName) {
		this.subredditName = subredditName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "PostRequest [postId=" + postId + ", subredditName=" + subredditName + ", postName=" + postName
				+ ", url=" + url + ", description=" + description + "]";
	}


    
}
