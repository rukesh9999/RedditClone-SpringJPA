package com.ReditClone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReditClone.dto.VoteDto;
import com.ReditClone.services.VoteService;

@RestController
@RequestMapping("/api")
public class VoteController {

	private final VoteService voteService;
	
	@Autowired
	public VoteController(VoteService voteService) {
		
		this.voteService = voteService;
	}



    @PostMapping("/vote")
	public ResponseEntity<?> vote(@RequestBody VoteDto voteDto)
	{
		voteService.vote(voteDto);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
}
