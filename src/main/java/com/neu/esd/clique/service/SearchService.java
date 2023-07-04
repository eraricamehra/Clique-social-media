package com.neu.esd.clique.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.esd.clique.model.User;

@Service
public class SearchService {
	
	@Autowired
	private UserService userService;
	
	public List<User> searchRegisteredUsers(String query) {
		List<User> searchedUsers = new ArrayList<>();
		Optional<User> fetchedUser = userService.getUserByEmail(query);
		if(fetchedUser != null) searchedUsers.add(fetchedUser.get());
//		searchedUsers.add(userService.getUserByUserName(query));
		if(query.contains(" ")) {
			String[] queryParams = query.split(" ");
			searchedUsers.addAll(userService.getUserByName(queryParams[0], queryParams[1]));
		} 
		searchedUsers.addAll(userService.getUserByName(query, null));
		return searchedUsers;
	}

}
