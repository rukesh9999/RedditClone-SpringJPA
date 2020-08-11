package com.ReditClone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;
import io.jsonwebtoken.Jwts;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ReditClone.Exception.SpringRedditException;

import io.jsonwebtoken.Claims;
import static io.jsonwebtoken.Jwts.parser;


@Service
public class JwtProvider {

	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
	private long jwtExpirationInMillis;

	private Logger mylogger = Logger.getLogger(getClass().getName());

	@PostConstruct
	public void init() {

		try {
			keyStore = KeyStore.getInstance("JKS");
			mylogger.info("keyStore...."+keyStore);
			InputStream resourceAsStream = getClass().getResourceAsStream("/content/springblog.jks");
			mylogger.info("resourceAsStream...."+resourceAsStream);

			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String generateToken(Authentication  authentication )
	{
		  User principal = (User) authentication.getPrincipal();   
		  
			mylogger.info("principal...."+principal);

		  
		 return  Jwts.builder()
		        .setSubject(principal.getUsername())
		        .signWith(getPrivateKey())
		        .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
		        .compact();
		
	}
	

	private PrivateKey getPrivateKey() {

		try {
			 
			PrivateKey PrivateKey = ((PrivateKey) keyStore.getKey("springblog", "secret".toCharArray()));
			mylogger.info("PrivateKey........."+PrivateKey);

			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
			
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {

			throw new SpringRedditException("Exception Occured While retrieving Public Key From keystore");
		}

	}

	public boolean validateToken(String jwt) {
		// TODO Auto-generated method stub
		mylogger.info(" .... validate Token method......");

		mylogger.info(" .... validate Token method......"+getPublickey());
		mylogger.info("validate Token Return Value ...."+Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt));

		
		
		Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
		
		return true;
	}

	private PublicKey getPublickey() {

		try {
			
			mylogger.info(" public key......"+keyStore.getCertificate("springblog").getPublicKey());

			return  keyStore.getCertificate("springblog").getPublicKey();
			  
		} catch (KeyStoreException e) {
			throw new SpringRedditException("Exception Occured while retrieving public key from keystore ");
		}
		
	}

	public String getUsernameFromJWT(String token) {
		// TODO Auto-generated method stub
		Claims claims =   parser()
				         .setSigningKey(getPublickey())
				         .parseClaimsJws(token)
				         .getBody();
		
		return claims.getSubject();
	}

	public Long getJwtExpirationInMillis() {
		  
		   return jwtExpirationInMillis;
		
	}

	
	public String generateToken(String username) {
		return   Jwts.builder().
				 setSubject(username)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}	 

	
	public String generateTokenWithUserName(String username) {
		return   Jwts.builder().
				 setSubject(username)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}	 

}
