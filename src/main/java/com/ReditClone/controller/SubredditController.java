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

import com.ReditClone.dto.SubredditDto;
import com.ReditClone.services.SubredditService;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

	private SubredditService  SubredditService;
	
	@Autowired
	public SubredditController(SubredditService subredditService) {
		
		SubredditService = subredditService;
	}


	@GetMapping("/getsubreddits")
	public ResponseEntity<List<SubredditDto>> getAllSubreddits()
	{
		return  ResponseEntity.status(HttpStatus.OK).body(SubredditService.getAll());
	}
	
	
	@GetMapping("/getsubreddit/{id}")
	public SubredditDto getSubreddit(@PathVariable Long id) 
	{
		return SubredditService.getSubreddit(id);
	}
	
	@PostMapping("/savesubreddit")
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody  SubredditDto subredditDto)
	{
		 return  ResponseEntity.status(HttpStatus.CREATED).body(SubredditService.save(subredditDto));
	}
	
}
