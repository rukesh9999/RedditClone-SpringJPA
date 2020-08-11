package com.ReditClone.dto;

import java.io.Serializable;

public class CommentsDto implements Serializable{
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long postId;
    private String createdDate;
    private String text;
    private String userName;
    
    
	public CommentsDto() {
		
		
	}
	
	public CommentsDto(Long id, Long postId, String createdDate, String text, String userName) {
		
		this.id = id;
		this.postId = postId;
		this.createdDate = createdDate;
		this.text = text;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CommentsDto [id=" + id + ", postId=" + postId + ", createdDate=" + createdDate + ", text=" + text
				+ ", userName=" + userName + "]";
	}
   
    
	
}
