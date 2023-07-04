package com.neu.esd.clique.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.SearchService;

@RestController
public class SearchController {
	
	
	@Autowired 
	private SearchService searchService;
	
	@GetMapping("/clique/q")
	public ResponseEntity<List<User>> searchUser(@RequestParam(value = "query")  String queryStr) {
		List<User> users = searchService.searchRegisteredUsers(queryStr);
		return ResponseEntity.ok(users);
	}

}
