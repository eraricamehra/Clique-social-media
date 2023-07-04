package com.neu.esd.clique.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.Post;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.PostService;

import jakarta.validation.Valid;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/clique/post/{postId}")
	public ResponseEntity<Post> getUserById(@PathVariable Integer postId) {
		if (postId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Post post = postService.getById(postId);

		if (post != null) {
			return ResponseEntity.ok(post);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Need to add method to fetch all posts of a particular user
	@GetMapping("/clique/feed/{userId}")
	public ResponseEntity<List<Post>> getFeed(@PathVariable Integer userId) {
		if (userId == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		List<Post> feed = postService.getFeed(userId);

		if (feed != null) {
			return ResponseEntity.ok(feed);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/clique/post")
	public ResponseEntity<?> createPost(@Valid @RequestBody Post post, BindingResult bindingResult) {

		Map<String, String> errorMap = postService.validatePost(bindingResult, post);
		if (errorMap != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);

		Post createdPost = postService.saveUser(post);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
	}

	@PatchMapping("/clique/like/{postId}")
	public ResponseEntity<Post> likePost(@PathVariable Integer postId) {
		if (postId == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		Post createdPost = postService.updateLike(postId, true);
		return ResponseEntity.status(HttpStatus.OK).body(createdPost);
	}

	@PatchMapping("/clique/dislike/{postId}")
	public ResponseEntity<Post> dislikePost(@PathVariable Integer postId) {
		if (postId == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		Post createdPost = postService.updateLike(postId, false);
		return ResponseEntity.status(HttpStatus.OK).body(createdPost);
	}

	// fetch all comments associated with a post

	@DeleteMapping("/clique/post/{postId}")
	public ResponseEntity<User> deletePost(@PathVariable Integer postId) {
		if (postId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Post existingPost = postService.getById(postId);
		if (existingPost == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		postService.deletePost(existingPost);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
