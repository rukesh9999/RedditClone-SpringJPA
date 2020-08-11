package com.ReditClone.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReditClone.dto.PostRequest;
import com.ReditClone.dto.PostResponse;
import com.ReditClone.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	@Autowired
	public PostController(PostService postService) {

		this.postService = postService;
	}
	
	
	@PostMapping("/savepost")
	public  ResponseEntity<?> createPost(@RequestBody PostRequest  postRequest )
	{
		postService.save(postRequest);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@GetMapping("/getpost/{id}")
	public ResponseEntity<PostResponse>  getPost(@PathVariable("id") Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("/getposts")
	public ResponseEntity<List<PostResponse>> getAllPosts()
	{
	   return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());	
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable("id") Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>>  getPostByUsername(@PathVariable("name")String username)
	{
	    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
}
