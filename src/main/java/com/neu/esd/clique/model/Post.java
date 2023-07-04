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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

@Entity
//@NamedQuery(name="updatesLikes", query="update Post p set p.likes = p.likes + 1 where postId =:postId") 
@NamedQuery(name="getAllFeed", query="select p from Post p where p.user.userId in (select c.connection from Connection c where c.user.userId =:userId ) or p.user.userId =:userId order by modified_date desc") 
@Table(name = "post")
public class Post {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
    @Column(name = "post_id")
	private int postId;
	
	@ManyToOne 
	@JoinColumn(name="fk_user_id")
	private User user;
	
	@NotBlank(message = "Content cannot be blank or null")
	@Column(name = "content")
	private String content;
	
	@Column(name = "likes")
	private int likes;
	
	@Column(name = "image_url")
	private String image_url;
	
	@CreationTimestamp
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@UpdateTimestamp
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();
	
	public Post() {
		
	}
	
	
	public Post(int postId, User user, String content, String image_url, Timestamp createdDate,
			Timestamp modifiedDate) {
		super();
		this.postId = postId;
		this.user = user;
		this.content = content;
		this.image_url = image_url;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
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
	
	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

}
