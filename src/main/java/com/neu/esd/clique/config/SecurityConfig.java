package com.neu.esd.clique.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.neu.esd.clique.dao.UserDao;
import com.neu.esd.clique.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig  {
	
	@Resource
    private UserDetailsService userDetailsService;

//	@Autowired
//	private UserDao userDAO;
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		System.out.println("*** UserDetailsService Bean is initialized ***");
//		return (userEmail) -> userDAO.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
//
////		return (userEmail) -> userDAO.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
//	}
	
	  @Bean
	  public UserDetailsService userDetailsService() {
	    return new CustomUserDetailsService();
	  }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		System.out.println("*** AuthenticationProvider Bean is initialized ***");
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService());
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
		System.out.println("*** AuthenticationManager Bean is initialized ***");
		return config.getAuthenticationManager() ;
	}
    
    
}