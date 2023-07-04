package com.neu.esd.clique.model;

public class LoginResponse {
	
	private User user;
	private String token;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LoginResponse(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
