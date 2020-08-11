package com.ReditClone.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	private JwtProvider JwtProvider;
	
	private UserDetailsService UserDetailsService;
	
	private Logger mylogger = Logger.getLogger(getClass().getName());

	
	@Autowired
	public JwtAuthenticationFilter(com.ReditClone.security.JwtProvider jwtProvider,
			org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
		
		JwtProvider = jwtProvider;
		UserDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		mylogger.info(" ....into dofiler method......");

		String jwt = getJwtFromRequest(request);

		mylogger.info(" jwt..... "+jwt);
		
		if(StringUtils.hasText(jwt)  &&  JwtProvider.validateToken(jwt))
		{
		
			mylogger.info(" ....validated token  method......");

			 String username = JwtProvider.getUsernameFromJWT(jwt);
			 UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
			 UsernamePasswordAuthenticationToken authentication =
					                  new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
			 
			 authentication.setDetails(new  WebAuthenticationDetailsSource().buildDetails(request));
			 SecurityContextHolder.getContext().setAuthentication(authentication);
			 
		}
		
		  filterChain.doFilter(request, response);
		
	}



	private String getJwtFromRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		    String bearerToken =  request.getHeader("Authorization");
		  
			mylogger.info("bearerToken......"+bearerToken);

		  if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
		  {
			  
				mylogger.info("bearerToken......"+bearerToken.substring(7));

			    return bearerToken.substring(7);
		  }
		  
		
		   return bearerToken;
	}

}
