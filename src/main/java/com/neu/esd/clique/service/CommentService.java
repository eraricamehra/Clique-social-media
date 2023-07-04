package com.neu.esd.clique.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.neu.esd.clique.dao.CommentDao;
import com.neu.esd.clique.model.Comment;
import com.neu.esd.clique.model.Post;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private PostService postService;

	// save
	public Comment saveUser(Comment user) {
		return commentDao.save(user);
	}

	public Comment getById(int userId) {
		return commentDao.findById(userId);
	}

	public List<Comment> getAllCommentsOfPost(int postId) {
		Post existingPost = postService.getById(postId);
		if (existingPost != null)
			return commentDao.getAllCommentsOfPost(postId);
		return null;
	}

	public Map<String, String> validateComment(BindingResult bindingResult, Comment comment) {
		Map<String, String> errorMap = null;
		List<String> list = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			errorMap = new HashMap<>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				list.add(error.getDefaultMessage());
			}
			errorMap.put("status", "400");
		}
		if (comment.getPost() == null) {
			if (errorMap == null)
				errorMap = new HashMap<>();
			list.add("Post is not associated with this request");
		}
		if (errorMap != null)
			errorMap.put("errorMap", list.toString());

		return errorMap;
	}

	public Comment updateComment(Comment comment) {
		return commentDao.update(comment);
	}

	// delete
	public void deleteComment(Comment comment) {
		commentDao.delete(comment);

	}

}
