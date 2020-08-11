package com.ReditClone.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes")
public class Vote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;
	
	private VoteType voteType;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",referencedColumnName = "userId")
	private User user;
	
	

	public Vote() {
		
	}

	
	
	public Vote(Long voteId, VoteType voteType, @NotNull Post post, User user) {
		super();
		this.voteId = voteId;
		this.voteType = voteType;
		this.post = post;
		this.user = user;
	}



	public Long getVoteId() {
		return voteId;
	}

	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", voteType=" + voteType + ", post=" + post + ", user=" + user + "]";
	}
	
	
	
	
	
}
