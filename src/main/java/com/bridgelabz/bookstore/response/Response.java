package com.bridgelabz.bookstore.response;

import java.time.LocalDateTime;

import com.bridgelabz.bookstore.model.User;

public class Response {
	private int status;
	private String message;
	private Object data;
	private LocalDateTime now;
	private User userDetails;

	public LocalDateTime getNow() {
		return now;
	}

	public void setNow(LocalDateTime now) {
		this.now = now;
	}

	public Response() {
		super();
	}

	public Response(String message) {
		super();
		this.message = message;
	}

	public Response(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public Response(String message, int status, Object data) {
		this.message = message;
		this.status = status;
		this.data = data;
	}
	
	public Response(String message, int status,Object data, LocalDateTime now,User userDetails) {
		this.message = message;
		this.status = status;
		this.data = data;
		this.now = now;
		this.userDetails = userDetails;
	}

	public Response(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Response(LocalDateTime now , String message,int status) {
		this.now = now;
		this.status = status;
		this.message = message;
	}

	public Response(String message, int status, User userDetails, LocalDateTime now) {
		super();
		this.status = status;
		this.message = message;
		this.userDetails = userDetails;
		this.now = now;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}
	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + "]";
	
}
}