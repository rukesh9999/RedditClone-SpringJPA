package com.ReditClone.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReditClone.Exception.SpringRedditException;
import com.ReditClone.model.RefreshToken;
import com.ReditClone.repositories.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	
	private Logger mylogger = Logger.getLogger(getClass().getName());

	
	@Autowired
	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		
		this.refreshTokenRepository = refreshTokenRepository;
	}


    @Transactional
    //@Cacheable(value = "RedditClone_RefreshToken_Cache")
	public RefreshToken generateRefreshToken() {
	   RefreshToken refreshToken =new RefreshToken();
	   refreshToken.setToken(UUID.randomUUID().toString());
	   refreshToken.setCreatedDate(Instant.now());
	   
	   return refreshTokenRepository.save(refreshToken);
	}
	
    @Transactional
	//@Cacheable(value = "RedditClone_RefreshToken_Cache", key = "#token", unless = "#result==null")
	public void validateRefreshToken(String token)
	{
		 refreshTokenRepository.findByToken(token).orElseThrow(()->new SpringRedditException("Invalid Refresh Token"));
	}
	
    
    @Transactional
	//@CacheEvict(value = "RedditClone_RefreshToken_Cache")
	public void deleteRefreshToken(String token) {
		
		mylogger.info("... in deletetoken method called........");

		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
		
		if(refreshToken.isPresent()) {
			refreshTokenRepository.deleteByToken(token);
			mylogger.info("... deletetoken method called........");

		}
		else {

			refreshToken.orElseThrow(()->new SpringRedditException("Invalid Refresh Token"));

		}

	}
	
	
}
