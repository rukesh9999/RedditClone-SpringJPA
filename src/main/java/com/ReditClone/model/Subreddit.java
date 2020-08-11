package com.ReditClone.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Builder;

@Entity
@Builder
public class Subreddit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long subredditId;
	
	@NotBlank(message = "Community name is required")
	private String name;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",referencedColumnName = "userId")
	private User user;




	
	
	public Subreddit() {
		
	}
	
	
	public Subreddit(Long subredditId, @NotBlank(message = "Community name is required") String name,
			@NotBlank(message = "Description is required") String description, List<Post> posts, Instant createdDate,
			User user) {
		
		this.subredditId = subredditId;
		this.name = name;
		this.description = description;
		this.posts = posts;
		this.createdDate = createdDate;
		this.user = user;
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


	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
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
		return "Subreddit [subredditId=" + subredditId + ", name=" + name + ", description=" + description + ", posts="
				+ posts + ", createdDate=" + createdDate + ", user=" + user + "]";
	}

	
	
  
	
  		
}
