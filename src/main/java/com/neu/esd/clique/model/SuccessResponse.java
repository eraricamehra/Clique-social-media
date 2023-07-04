package com.neu.esd.clique.model;

public class SuccessResponse {
	
	private String status;
	private String message;
	public String getStatus() {
		return status;
	}
	public SuccessResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
