package com.ReditClone.services;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReditClone.Exception.PostNotFoundException;
import com.ReditClone.Exception.SpringRedditException;
import com.ReditClone.dto.VoteDto;
import com.ReditClone.model.Post;
import com.ReditClone.model.Vote;
import com.ReditClone.model.VoteType;
import com.ReditClone.repositories.PostRepository;
import com.ReditClone.repositories.VoteRepository;

@Service
public class VoteService {

	private final VoteRepository VoteRepository;
	
	private final PostRepository PostRepository;
	
	private final Authservice authservice;
	
	private Logger mylogger = Logger.getLogger(getClass().getName());

	
    @Autowired
	public VoteService(com.ReditClone.repositories.VoteRepository voteRepository,
			com.ReditClone.repositories.PostRepository postRepository, Authservice authservice) {
		
		VoteRepository = voteRepository;
		PostRepository = postRepository;
		this.authservice = authservice;
	}


	@Transactional
	public void vote(VoteDto voteDto) {

		 Post post =  PostRepository.findById(voteDto.getPostId()).orElseThrow(()->new PostNotFoundException("Post Not Found with Id.."+voteDto.getPostId()));
		 mylogger.info("find post by id..."+post);
		 
		 Optional<Vote> voteByPostAndUser  =  VoteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authservice.getCurrentUser());
	     
		 mylogger.info("voteByPostAndUser....."+voteByPostAndUser);

		 
	     if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType()))  
	      {
	    	 throw new SpringRedditException("you have already "+voteDto.getVoteType()+"'d for this Post");  		
	      }
	     
	     mylogger.info("voteDto.getVoteType()...."+voteDto.getVoteType());
	     
	     mylogger.info("VoteType.UPVOTE.equals()...."+VoteType.UPVOTE.equals(voteDto.getVoteType()));

	      if(VoteType.UPVOTE.equals(voteDto.getVoteType())){
	    	  post.setVoteCount(post.getVoteCount()+1);
	      }
	      
	      else {
	    	  post.setVoteCount(post.getVoteCount()-1);
	      }
	     
	      VoteRepository.save(mapToVote(voteDto, post));
	      PostRepository.save(post);
	}
	     
	
	      
	  private Vote mapToVote(VoteDto voteDto, Post post) {
		Vote vote = new Vote();
		vote.setPost(post);
		vote.setVoteType(voteDto.getVoteType());
		vote.setUser(authservice.getCurrentUser());
		return vote;
	 }
	      
	
}
