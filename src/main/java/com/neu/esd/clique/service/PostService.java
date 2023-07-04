package com.neu.esd.clique.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.neu.esd.clique.dao.PostDao;
import com.neu.esd.clique.model.Post;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	// save
	public Post saveUser(Post user) {
		return postDao.save(user);
	}

	public Post getById(int userId) {
		return postDao.findById(userId);
	}

	public List<Post> getFeed(Integer userId) {
		return postDao.getAllPostForFeed(userId);
	}

	public Post updatePost(Post post) {
		return postDao.update(post);
	}

	public Post updateLike(int postId, boolean like) {
		Post existingPost = postDao.findById(postId);
		if(existingPost == null) return null;
		if(like)
		existingPost.setLikes(existingPost.getLikes() + 1);
		else existingPost.setLikes(existingPost.getLikes() - 1);
		return updatePost(existingPost);
	}

	// delete
	public void deletePost(Post post) {
		postDao.delete(post);

	}

	public Map<String, String> validatePost(BindingResult bindingResult, Post post) {
		Map<String, String> errorMap = null;
		List<String> list = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			errorMap = new HashMap<>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				list.add(error.getDefaultMessage());
			}
			errorMap.put("status", "400");
		}
		if (post.getUser() == null) {
			if (errorMap == null)
				errorMap = new HashMap<>();
			list.add("User is no associated with this request");
		}
		if (errorMap != null)
			errorMap.put("errorMap", list.toString());

		return errorMap;
	}

}
