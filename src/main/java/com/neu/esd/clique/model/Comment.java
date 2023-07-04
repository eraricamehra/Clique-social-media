package com.neu.esd.clique.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;

@NamedQuery(name="getCommentsOfPost", query="select c from Comment c where c.post.postId =:postId") 
@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
    @Column(name = "comment_id")
	private int commentId;
	
	@ManyToOne
	@JoinColumn(name="fk_post")
	private Post post;
	
	@NotBlank(message = "Content cannot be blank or null")
	@Column(name = "content")
	private String content;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	
	public Comment() {
		
	}
	
	public Comment(int commentId,Post post, String content, Timestamp createdDate,
			Timestamp modifiedDate) {
		super();
		this.commentId = commentId;
		this.post = post;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}
