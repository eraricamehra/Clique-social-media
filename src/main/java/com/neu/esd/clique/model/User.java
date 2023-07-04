package com.neu.esd.clique.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@NamedQuery(name="findByFullName", query="select u from User u where u.firstName like :firstName and u.lastName like :lastName") 
@NamedQuery(name="findByEmail", query="select u from User u where u.email  =:email") 
@NamedQuery(name="findByFirstName", query="select u from User u where u.firstName like :firstName or u.username like :username") 
@NamedQuery(name="findAll", query="select u from User u")
@NamedQuery(name="findByEmailAndPassword", query="select u from User u where u.email  =:email and  u.password =:password") 
@Table(name = "user")
public class User implements Serializable, UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="native")
    @Column(name = "user_id")
	private Integer userId;
	
	@NotBlank(message = "Username cannot be blank or null")
	@Column(name = "username")
	private String username;
	
	@NotBlank(message = "Password cannot be blank or null")
	@Column(name = "password")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotBlank(message = "Email cannot be blank or null")
	@Email(message = "Email must be a well-formed email address")
	@Column(name = "email")
	private String email;
	
	
	@NotBlank(message = "Firstname cannot be blank or null")
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank(message = "Lastname cannot be blank or null")
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "image_url")
	private String profileImageUrl;
	
    @CreationTimestamp
	@Column(name = "created_date")
	private Timestamp createdDate;
	
    @UpdateTimestamp
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "user_role")
	private String role;
	
	@Column(name = "about")
	private String about;
	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}
	@Column(name = "interests")
	private String interests;
	
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
	private List<Request> requests = new ArrayList<>();
	
	@OneToMany(mappedBy = "connection", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Connection> connections = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();

	
	public User() {
		
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public User(Integer userId, String username, String password, String email, String firstName, String lastName,
			String profileImageUrl, Timestamp createdDate, Timestamp modifiedDate) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImageUrl = profileImageUrl;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}



}
