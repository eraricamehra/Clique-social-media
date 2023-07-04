package com.neu.esd.clique.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.neu.esd.clique.service.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return "/auth/login".equals(path);
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("*** Inside  doFilterInternal method in  ***");
		String authorizationHeader = request.getHeader("Authorization");
		String jwt;
		String userEmail;
		
		//Checking if auth Header is present
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			System.out.println("*** AUTH HEADER IS NOT PRESENT ***");
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authorizationHeader.substring(7);
//		System.out.println(jwt);
		userEmail = jwtUtil.extractUserEmail(jwt);
		
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		if(userEmail != null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			
			if(jwtUtil.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
		}
		filterChain.doFilter(request, response);
		
	}
	
}
