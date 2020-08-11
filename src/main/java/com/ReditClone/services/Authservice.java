package com.ReditClone.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ReditClone.Exception.SpringRedditException;
import com.ReditClone.dto.AuthenticationResponse;
import com.ReditClone.dto.LoginRequest;
import com.ReditClone.dto.NotificationEmail;
import com.ReditClone.dto.RegisterRequest;
import com.ReditClone.model.RefreshTokenRequest;
import com.ReditClone.model.User;
import com.ReditClone.model.VerificationToken;
import com.ReditClone.repositories.UserRepository;
import com.ReditClone.repositories.VerificationTokenRepository;
import com.ReditClone.security.JwtProvider;
import com.ReditClone.utils.Constants;

@Service
public class Authservice {

	private static String token = "";

	private Logger mylogger = Logger.getLogger(getClass().getName());

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final VerificationTokenRepository VerificationTokenRepository;

	private final AuthenticationManager authenticationManager;


	private final JwtProvider jwtProvider;

	private MailService mailService;
	
	private RefreshTokenService refreshTokenService;

	String message = "Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
			+ Constants.ACTIVATION_EMAIL + "/";

	@Autowired
	public Authservice( PasswordEncoder passwordEncoder, UserRepository userRepository,
			com.ReditClone.repositories.VerificationTokenRepository verificationTokenRepository,
			AuthenticationManager authenticationManager,   JwtProvider jwtProvider,
			MailService mailService, RefreshTokenService refreshTokenService) {
		
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		VerificationTokenRepository = verificationTokenRepository;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.mailService = mailService;
		this.refreshTokenService = refreshTokenService;
	}

	
	
	
	@Transactional
	public void signUp(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		user.setCreatedDate(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);
		token = generateVerificationToken(user);

		message = message + token;

		mylogger.info("message....." + message);

		mailService.sendMail(new NotificationEmail("please Activate Your Account", user.getEmail(), message));
	}

	
	public String generateVerificationToken(User user) {

		token = UUID.randomUUID().toString();

		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		VerificationTokenRepository.save(verificationToken);
		return token;

	}

	public void verifyAccount(String token) {
		// TODO Auto-generated method stub
		Optional<VerificationToken> VerificationToken = VerificationTokenRepository.findByToken(token);
		VerificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchuserAndEnable(VerificationToken.get());

	}
	
	@Transactional(readOnly = true)
	//@Cacheable(value = "RedditClone_CurrentUser_Cache")
	public User getCurrentUser()
	{
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		mylogger.info("principal......" + principal.getUsername());
		return userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}

	@Transactional
	private void fetchuserAndEnable(VerificationToken verificationToken) {
		
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new SpringRedditException("User Not Found with name..." + username));
		user.setEnabled(true);
		userRepository.save(user);
		
	}
	
	@Transactional
	public AuthenticationResponse login(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
						                               loginRequest.getPassword()));
		mylogger.info("authentication....." + authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
        
		mylogger.info("authentication name..."+SecurityContextHolder.getContext().getAuthentication().getName());
		String authenticationToken = jwtProvider.generateToken(authentication);

		mylogger.info("authenticationToken....." + authenticationToken);
		
		return new AuthenticationResponse(authenticationToken, 
				   loginRequest.getUsername(), 
				   Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
				   refreshTokenService.generateRefreshToken().getToken());
	}

	
	private String encodePassword(String password) {

		return passwordEncoder.encode(password);
	}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		
		
		 refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		 String authenticationToken = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
		 
		 return new AuthenticationResponse
				       (authenticationToken, refreshTokenRequest.getUsername(), 
					    Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
						refreshTokenRequest.getRefreshToken());
	}
	
	
	
	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken	) && 
				 authentication.isAuthenticated();
		}
	

}
