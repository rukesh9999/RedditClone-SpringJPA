package com.ReditClone.services;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReditClone.Exception.SubredditNotFoundException;
import com.ReditClone.dto.SubredditDto;
import com.ReditClone.model.Subreddit;
import com.ReditClone.repositories.SubredditRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Service
public class SubredditService {

	private final SubredditRepository subredditRepository;
	
	private final Authservice authservice;
	
	private Logger mylogger = Logger.getLogger(getClass().getName());

	
	@Autowired
	public SubredditService(SubredditRepository subredditRepository, Authservice authservice) {
		
		this.subredditRepository = subredditRepository;
		this.authservice = authservice;
		
	}


	@Transactional(readOnly = true)
	//@Cacheable(cacheNames = {"RedditClone_Subreddit_Cache"})
	public List<SubredditDto> getAll() {
		// TODO Auto-generated method stub
		return subredditRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	
	
	
	

	@Transactional(readOnly = true)
	//@Cacheable(value = "RedditClone_Subreddit_Cache", key = "#id", unless = "#result==null")
	public SubredditDto getSubreddit(Long id) {
		// TODO Auto-generated method stub
		Subreddit subreddit =  subredditRepository.findById(id).orElseThrow(()->new SubredditNotFoundException("Subreddit not found with id -" + id));
		
		return mapToDto(subreddit);
	}
  

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		
		 Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDto));
		 subredditDto.setSubredditId(subreddit.getSubredditId());
		 return subredditDto;
	}
	
	
	public SubredditDto mapToDto(Subreddit subreddit)
	{
		SubredditDto subredditDto =new SubredditDto();
		
		subredditDto.setDescription(subreddit.getDescription());
		subredditDto.setCreatedDate(getDuration(subreddit));
		subredditDto.setName(subreddit.getName());
		subredditDto.setSubredditId(subreddit.getSubredditId());
		subredditDto.setUser(subreddit.getUser().getUsername());
		
		return subredditDto;
		
	}
	
	
	public Subreddit mapToSubreddit(SubredditDto subredditDto)
	{
		Subreddit subreddit =new Subreddit();
		subreddit.setDescription(subredditDto.getDescription());
		subreddit.setName(subredditDto.getName());
		subreddit.setUser(authservice.getCurrentUser());
		subreddit.setCreatedDate(Instant.now());
		
		mylogger.info("subreddit....."+subreddit.getSubredditId());
		return subreddit;		
               
	}
	
	 String getDuration(Subreddit subreddit) {
	        return TimeAgo.using(subreddit.getCreatedDate().toEpochMilli());
	    }

}
