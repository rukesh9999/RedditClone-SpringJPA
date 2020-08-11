package com.ReditClone.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;


@Entity
@Table(name = "posts")
public class Post  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
    
    @NotBlank(message = "Post Name Cannot be empty or Null ")
	private String postname;
    
    @Nullable
	private String url;
    
    @Nullable
    @Lob
	private String description;
    
    private Integer voteCount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    
    private Instant createdDate;
    
    @ManyToOne
    @JoinColumn(name = "subredditId",referencedColumnName = "subredditId")
    private Subreddit subreddit;

	public Post() {
		
	}

	public Post(Long postId, @NotBlank(message = "Post Name Cannot be empty or Null ") String postname, String url,
			String description, Integer voteCount, User user, Instant createdDate, Subreddit subreddit) {
	
		this.postId = postId;
		this.postname = postname;
		this.url = url;
		this.description = description;
		this.voteCount = voteCount;
		this.user = user;
		this.createdDate = createdDate;
		this.subreddit = subreddit;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
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

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Subreddit getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(Subreddit subreddit) {
		this.subreddit = subreddit;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postname=" + postname + ", url=" + url + ", description=" + description
				+ ", voteCount=" + voteCount + ", user=" + user + ", createdDate=" + createdDate + ", subreddit="
				+ subreddit + "]";
	}
    
	
	
	
}
