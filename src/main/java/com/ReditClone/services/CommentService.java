package com.ReditClone.services;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ReditClone.Exception.PostNotFoundException;
import com.ReditClone.dto.CommentsDto;
import com.ReditClone.dto.NotificationEmail;
import com.ReditClone.model.Comment;
import com.ReditClone.model.Post;
import com.ReditClone.model.User;
import com.ReditClone.repositories.CommentRepository;
import com.ReditClone.repositories.PostRepository;
import com.ReditClone.repositories.UserRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Service
public class CommentService {

    private static final String POST_URL = "";

	private final CommentRepository commentRepository;
	
	private final PostRepository PostRepository;
	
	private final Authservice Authservice;
	
    private final MailContentBuilder mailContentBuilder;

	private final MailService MailService;
	private final UserRepository UserRepository;
	private Logger mylogger = Logger.getLogger(getClass().getName());

	
	@Autowired
	public CommentService(CommentRepository commentRepository,
			com.ReditClone.repositories.PostRepository postRepository, com.ReditClone.services.Authservice authservice,
			MailContentBuilder mailContentBuilder, com.ReditClone.services.MailService mailService,
			com.ReditClone.repositories.UserRepository userRepository) {
	
		this.commentRepository = commentRepository;
		PostRepository = postRepository;
		Authservice = authservice;
		this.mailContentBuilder = mailContentBuilder;
		MailService = mailService;
		UserRepository = userRepository;
	}


	

    @Transactional
	public void save(CommentsDto commentsDto) {
		// TODO Auto-generated method stub
    	mylogger.info("....comment dto...."+commentsDto);
	  Post  post = PostRepository.findById(commentsDto.getPostId())
			  .orElseThrow(()->new PostNotFoundException(commentsDto.getPostId().toString()));
	         
	  User currentuser = Authservice.getCurrentUser();
	  
	  Comment comment =new Comment();
	  
	  comment.setCreatedDate(Instant.now());
	  comment.setId(commentsDto.getId());
	  comment.setPost(post);
	  comment.setText(commentsDto.getText());
	  comment.setUser(currentuser);
	  
	  commentRepository.save(comment);
	  
	  String message = mailContentBuilder.build(post.getUser().getUsername()+"..Posted a Comment on your Post.."+POST_URL);
		
	  sendCommentNotification(message,post.getUser());
	  
	}

    
	private void sendCommentNotification(String message, User user) {
		// TODO Auto-generated method stub
		MailService.sendMail(
				new NotificationEmail(user.getUsername() + "..commented on your post", user.getEmail(), message));

	}

    @Transactional
	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		// TODO Auto-generated method stub
        Post post = PostRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

        List<Comment> comments = commentRepository.findByPost(post);
        
        return   comments.stream()
        		.map(this::mapToDto)
        		.collect(Collectors.toList());
                
	}

    @Transactional
	public List<CommentsDto> getAllCommentsForUser(String userName) {
		// TODO Auto-generated method stub
        User user = UserRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        List<Comment> comments = commentRepository.findAllByUser(user);
		
		return comments.stream()
				        .map(this::mapToDto)
				        .collect(Collectors.toList());
	}
	
	public CommentsDto mapToDto(Comment comments)
	{
		CommentsDto CommentsDto =new CommentsDto();
		CommentsDto.setCreatedDate(getDuration(comments));
		CommentsDto.setId(comments.getId());;
		CommentsDto.setPostId(comments.getPost().getPostId());
		CommentsDto.setText(comments.getText());
		CommentsDto.setUserName(comments.getUser().getUsername());
				
		return CommentsDto;
		
	}
	
	  String getDuration(Comment comments) {
	        return TimeAgo.using(comments.getCreatedDate().toEpochMilli());
	    }



}
