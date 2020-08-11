package com.ReditClone.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity 
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId" , referencedColumnName = "postId")
	private Post post;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",referencedColumnName = "userId")
	private User user;

	public Comment() {
		
	}

	public Comment(Long id, @NotEmpty String text, Post post, Instant createdDate, User user) {
		
		this.id = id;
		this.text = text;
		this.post = post;
		this.createdDate = createdDate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", post=" + post + ", createdDate=" + createdDate + ", user="
				+ user + "]";
	}
	
	
	
	
	
	
}
