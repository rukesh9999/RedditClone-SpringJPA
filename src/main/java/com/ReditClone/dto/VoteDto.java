package com.ReditClone.dto;

import java.io.Serializable;

import com.ReditClone.model.VoteType;

public class VoteDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private VoteType voteType;
    private Long postId;
    
	public VoteDto() {

	}
	
	public VoteDto(VoteType voteType, Long postId) {
		this.voteType = voteType;
		this.postId = postId;
	}
	
	
	public VoteType getVoteType() {
		return voteType;
	}
	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	
	
	@Override
	public String toString() {
		return "VoteDto [postId=" + postId + "]";
	}

    
}
