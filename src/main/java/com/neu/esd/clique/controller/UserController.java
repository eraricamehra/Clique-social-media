package com.neu.esd.clique.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.Connection;
import com.neu.esd.clique.model.Post;
import com.neu.esd.clique.model.Request;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.ConnectionService;
import com.neu.esd.clique.service.PostService;
import com.neu.esd.clique.service.RequestService;
import com.neu.esd.clique.service.SearchService;
import com.neu.esd.clique.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private PostService postService;

	@GetMapping("/hello")
	public ResponseEntity<List<User>> test() {
		List<User> users = searchService.searchRegisteredUsers("johndoe");
		return ResponseEntity.ok(users);
	}

	@GetMapping("/clique/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable int userId, HttpServletRequest req) {
		User user = userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);

	}

	@GetMapping("/clique/users")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(users);

	}

	@PostMapping("/clique/register/")
	public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult bindingResult) {

		Map<String, String> errorMap = userService.validate(bindingResult, user);
		if (errorMap != null && !errorMap.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		User createdUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.OK).body(createdUser);
	}

	@PatchMapping("/clique/user/{userId}")
	public ResponseEntity<User> updateUserProfile(@PathVariable int userId, @RequestBody User user,
			HttpServletRequest req) {
		System.out.println("******");
		System.out.println(user.getUsername());
		if (userId == 0 || user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		User updatedUser = userService.updateUserProfile(userId, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/clique/user/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable int userId) {
		System.out.println("Inside deleting user");
		if (userId == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		User existingUser = userService.getUserById(userId);
		if (existingUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// check for connections and requests before deleting
		List<Connection> connections = connectionService.getAllConnections(existingUser.getUserId());
		connections.addAll(connectionService.getAllConnectionsByReceiver(existingUser.getUserId()));
		for (Connection con : connections) {
			connectionService.delete(con);
		}
		System.out.println("Connections deleted");
		List<Request> requests = requestService.getRequestBySenderAndReceiver(existingUser.getUserId());
		for (Request req : requests) {
			requestService.deleteeRequest(req);
		}
		System.out.println("Request deleted");
		List<Post> feed = postService.getFeed(userId);
		for (Post post : feed) {
			postService.deletePost(post);
		}
		System.out.println("Posts deleted");
		userService.deleteUser(existingUser);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
