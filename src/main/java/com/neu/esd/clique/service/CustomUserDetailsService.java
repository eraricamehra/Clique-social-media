package com.neu.esd.clique.service;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.neu.esd.clique.dao.UserDao;
import com.neu.esd.clique.model.UserRole;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		com.neu.esd.clique.model.User user = userDao.findByEmail(username).get();
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}

	public List<GrantedAuthority> getGrantedAuthorities(com.neu.esd.clique.model.User user) {
		List<GrantedAuthority> roles = null;
		List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority authorityUser = new SimpleGrantedAuthority("USER");
		updatedAuthorities.add(authorityUser);
		if (user.getRole() == "ADMIN") {
			SimpleGrantedAuthority authorityAdmin = new SimpleGrantedAuthority("ADMIN");
			updatedAuthorities.add(authorityAdmin);
		} 
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), updatedAuthorities));

		return roles;
	}
}
