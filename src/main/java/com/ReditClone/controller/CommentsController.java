package com.ReditClone.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReditClone.dto.CommentsDto;
import com.ReditClone.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

	private final CommentService commentService;

	private Logger mylogger = Logger.getLogger(getClass().getName());

	@Autowired
	public CommentsController(CommentService commentService) {

		this.commentService = commentService;
	}

	@Transactional
	@PostMapping("/savecomment")
	public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto) {
		mylogger.info("...from controller comment dto....."+commentsDto);
		commentService.save(commentsDto);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

	@GetMapping("/comments-by-post/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable("postId") Long postId) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(commentService.getAllCommentsForPost(postId));
	}

	
	@GetMapping("/comments-by-user/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable("userName") String userName) {
		return    ResponseEntity
				.status(HttpStatus.OK)
				.body(commentService.getAllCommentsForUser(userName));
	}

}
