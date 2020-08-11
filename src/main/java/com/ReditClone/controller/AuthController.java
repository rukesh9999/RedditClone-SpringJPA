package com.ReditClone.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReditClone.dto.AuthenticationResponse;
import com.ReditClone.dto.LoginRequest;
import com.ReditClone.dto.RegisterRequest;
import com.ReditClone.model.RefreshTokenRequest;
import com.ReditClone.services.Authservice;
import com.ReditClone.services.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    private final Authservice authservice;
    
    private final RefreshTokenService refreshTokenService;
	
	private Logger mylogger = Logger.getLogger(getClass().getName());

    @Autowired
	public AuthController(Authservice authservice, RefreshTokenService refreshTokenService) {
	
		this.authservice = authservice;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest)
	{
		authservice.signUp(registerRequest);
		
		return new ResponseEntity<>("User Registration SuccesFul!!..",HttpStatus.OK);
	}
	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token)
	{
		authservice.verifyAccount(token);
		
		return new ResponseEntity<String>("Account Activated Successfully...!!!", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest  LoginRequest) {
		
		mylogger.info("hiiiiiiiiii"+LoginRequest.getUsername()+"..........."+LoginRequest.getPassword());
		return authservice.login(LoginRequest);
	}
	
	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshtoken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
	{
	   	return authservice.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
	{
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return  ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!....");
	}
	
}
