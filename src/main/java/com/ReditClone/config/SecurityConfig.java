package com.ReditClone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ReditClone.security.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	private JwtAuthenticationFilter  JwtAuthenticationFilter;
	
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService,
			        JwtAuthenticationFilter jwtAuthenticationFilter) {
	
		
		this.userDetailsService = userDetailsService;
		JwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		// TODO Auto-generated method stub
		httpSecurity.csrf().disable().
		           authorizeRequests()
		           .antMatchers("/api/auth/**","/api/posts/**")
		           .permitAll()
		           .antMatchers("/api/subreddit/**","/api/vote")
		           .permitAll()
		           .antMatchers("/api/comments/**")
		           .permitAll()
		           .antMatchers("/api/posts/")
		           	.permitAll()
		           .antMatchers("/api/posts/**")
		           .permitAll()
		           .antMatchers("/api/**",
		        		   "/swagger-ui.html", 
		        		   "/webjars/**",
		        		   "/v2/**", 
		        		   "/swagger-resources/**").anonymous()

		           .antMatchers("/v2/api-docs",
		           "/configuration/ui",
		           "/swagger-resources/**",
		           "/configuration/security",
		           "/swagger-ui.html",
		           "/webjars/**")
		           .permitAll().anyRequest()
				   .authenticated();
		
		httpSecurity.addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager  authenticationManager () throws Exception
	{
		return super.authenticationManager();
		
	}	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}


