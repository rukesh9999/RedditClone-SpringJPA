package com.ReditClone.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReditClone.Exception.PostNotFoundException;
import com.ReditClone.Exception.SubredditNotFoundException;
import com.ReditClone.dto.PostRequest;
import com.ReditClone.dto.PostResponse;
import com.ReditClone.model.Post;
import com.ReditClone.model.Subreddit;
import com.ReditClone.model.User;
import com.ReditClone.repositories.CommentRepository;
import com.ReditClone.repositories.PostRepository;
import com.ReditClone.repositories.SubredditRepository;
import com.ReditClone.repositories.UserRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Service
public class PostService {

	
	private final SubredditRepository subredditRepository;
	
	private final Authservice authservice;
	
	private final UserRepository userRepository;
	
	private final PostRepository postRepository;
	
	private final CommentRepository CommentRepository;
	
	@Autowired
	public PostService(SubredditRepository subredditRepository, Authservice authservice, UserRepository userRepository,
			PostRepository postRepository, com.ReditClone.repositories.VoteRepository voteRepository,
			com.ReditClone.repositories.CommentRepository commentRepository) {
		this.subredditRepository = subredditRepository;
		this.authservice = authservice;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		CommentRepository = commentRepository;
	}

	
	@Transactional
	public void save(PostRequest postRequest) {
		// TODO Auto-generated method stub
		Subreddit subreddit =subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(()->new SubredditNotFoundException(postRequest.getSubredditName()));
		 	
		User currentuser = authservice.getCurrentUser();
		
		Post post =new Post();
		post.setDescription(postRequest.getDescription());
		post.setCreatedDate(Instant.now());
		post.setPostId(postRequest.getPostId());
		post.setPostname(postRequest.getPostName());
		post.setSubreddit(subreddit);
		post.setUrl(postRequest.getUrl());
		post.setUser(currentuser);
		post.setVoteCount(0);
	
		postRepository.save(post);
		
	}

	
	@Transactional(readOnly = true)
	//@Cacheable(cacheNames = {"RedditClone_Post_Cache"})
	public List<PostResponse> getAllPosts() {
		// TODO Auto-generated method stub
		return postRepository.findAll()
		                     .stream()
		                     .map(this::mapToDto)
		                     .collect(Collectors.toList());
		
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id1) {
		// TODO Auto-generated method stub
		
		Post post  = postRepository.findById(id1).
				   orElseThrow(()->new PostNotFoundException(id1.toString()));
		
		PostResponse postResponse =new PostResponse();
		postResponse.setDescription(post.getDescription());
		postResponse.setPostId(post.getPostId());
		postResponse.setPostName(post.getPostname());
		postResponse.setSubredditName(post.getSubreddit().getName());
		postResponse.setUrl(post.getUrl());
		postResponse.setCommentCount(commentCount(post));
		postResponse.setDuration(getDuration(post));
		postResponse.setVoteCount(post.getVoteCount());
		postResponse.setUserName(post.getUser().getUsername());
		return  postResponse;	
	}


	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subRedditId) {
		// TODO Auto-generated method stub
		Subreddit subReddit = subredditRepository.findById(subRedditId).orElseThrow(()->new SubredditNotFoundException(subRedditId.toString()));
	    List<Post> posts =  postRepository.findAllBySubreddit(subReddit);
	    
		return posts.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	//@Cacheable(value = "RedditClone_Post_Cache", key = "#username", unless = "#result==null")
	public List<PostResponse> getPostsByUsername(String username) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByUsername(username)
				                   .orElseThrow(()-> new UsernameNotFoundException(username));
		return postRepository.findByUser(user)
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
				              
	}

	
	public PostResponse mapToDto(Post post)
	{
		PostResponse postResponse =new PostResponse();
		postResponse.setPostName(post.getPostname());
		postResponse.setPostId(post.getPostId());
		postResponse.setDescription(post.getDescription());
		postResponse.setSubredditName(post.getSubreddit().getName());
		postResponse.setUrl(post.getUrl());
		postResponse.setCommentCount(commentCount(post));
		postResponse.setDuration(getDuration(post));
		postResponse.setVoteCount(post.getVoteCount());
		postResponse.setUserName(post.getUser().getUsername());
		return postResponse;
		
	}
	
    Integer commentCount(Post post) {    	
        return CommentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

	
}
