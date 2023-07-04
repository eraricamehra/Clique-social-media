package com.neu.esd.clique.model;


import jakarta.validation.constraints.NotBlank;


public class LoginRequest {
	
    @NotBlank(message = "Email cannot be blank or null")
	private String email;
	
    @NotBlank(message = "Password cannot be blank or null")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
