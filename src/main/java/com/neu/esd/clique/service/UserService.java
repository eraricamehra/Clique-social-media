package com.neu.esd.clique.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.neu.esd.clique.dao.UserDao;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.model.UserRole;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// save
	public User saveUser(User user) {
		user.setRole(UserRole.USER.toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.save(user);
	}

	// get
	public User getUser(User user) {
		return userDao.findByUsername(user.getUsername());
	}

	public User getUserById(int userId) {
		return userDao.findById(userId);
	}

	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}
	
	public List<User> getUserByName(String firstName, String lastName) {
		if(lastName == null) return userDao.findByFirstName(firstName);
		return userDao.findByName(firstName, lastName);
	}

	public Optional<User> getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public User getUserByEmailAndPassword(String email, String password) {
		passwordEncoder.encode(password);
		User user = userDao.findByEmailAndPassword(email, password);
		if (user != null && passwordEncoder.matches(password, user.getPassword()))
			return user;
		return null;

	}

	public User getUserByUserName(String username) {
		return userDao.findByUsername(username);
	}

	// update
	public User updateUserProfile(int userId, User updatedUser) {
		User existingUser = userDao.findById(userId);
		if (existingUser != null) {
			existingUser.setUsername(updatedUser.getUsername());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setProfileImageUrl(updatedUser.getProfileImageUrl());
			System.out.println(existingUser.getUsername());
			return userDao.update(existingUser);
		}
		return null;
	}

	// delete
	public void deleteUser(User user) {
		userDao.delete(user);

	}

	public Map<String, String> validate(BindingResult bindingResult, User user) {
		Map<String, String> errorMap = null;
		List<String> list = new ArrayList<>();

		if (bindingResult.hasErrors()) {
			errorMap = new HashMap<>();
			
			for (ObjectError error : bindingResult.getAllErrors()) {
				list.add(error.getDefaultMessage());
			}
			errorMap.put("errorMap", list.toString());
			errorMap.put("status", "400");
		} 
		
		if(user.getEmail()!= null && user.getEmail() != "") {
			if(errorMap == null) errorMap = new HashMap<>();
			Optional<User> existing = getUserByEmail(user.getEmail());
			if(existing != null && existing.get() != null) {
			list.add("Email already exists");
			errorMap.put("errorMap", list.toString());
			errorMap.put("status", "400");
			}
		}
		
		return errorMap;
	}

}
