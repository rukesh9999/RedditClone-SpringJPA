package com.ReditClone.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReditClone.model.User;
import com.ReditClone.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	
	
	@Transactional(readOnly = true)
	//@Cacheable(value = "RedditClone_UserDetails_Cache", key = "#username", unless = "#result==null")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		Optional<User>  userOptional = userRepository.findByUsername(username);

		User user = userOptional
				    .orElseThrow(() -> new UsernameNotFoundException("No User " + "Found with UserName :::::" + username));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {

		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}



	
}
