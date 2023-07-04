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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.esd.clique.model.Comment;
import com.neu.esd.clique.model.User;
import com.neu.esd.clique.service.CommentService;

import jakarta.validation.Valid;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

//	@GetMapping("/clique/comment/{commentId}")
//	public ResponseEntity<Comment> getUserById(@PathVariable Long commentId) {
//		Comment comment = commentService.getById(commentId);
//		if (comment != null) {
//			return ResponseEntity.ok(comment);
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//	}

	@GetMapping("/clique/comment/{postId}")
	public ResponseEntity<?> getAllCommentsOfPost(@PathVariable Integer postId) {
		List<Comment> comments = commentService.getAllCommentsOfPost(postId);
		if (comments != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(comments);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/clique/comment")
	public ResponseEntity<?> createComment(@Valid @RequestBody Comment comment, BindingResult bindingResult) {
		Map<String, String> errorMap = commentService.validateComment(bindingResult, comment);
		if (errorMap != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
		Comment createdComment = commentService.saveUser(comment);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
	}

	// get all comments of a post

//	@PatchMapping("/like/{commentId}")
//	public ResponseEntity<Comment> updateComment(@PathVariable Long commentId) {
//		Comment createdComment = commentService.updateComment(commentService.getById(commentId));
//		return ResponseEntity.status(HttpStatus.OK).body(createdComment);
//	}

	@DeleteMapping("/clique/comment/{commentId}")
	public ResponseEntity<User> deleteComment(@PathVariable Integer commentId) {
		if (commentId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Comment existingComment = commentService.getById(commentId);
		if (existingComment == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		commentService.deleteComment(existingComment);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
