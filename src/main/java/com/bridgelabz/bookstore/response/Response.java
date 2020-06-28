package com.bridgelabz.bookstore.response;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.User;

@Component
public class Response {

	// Kalpesh Review: Create seprate response class as per requirment and what is
	// now?, what is userDetails?, what is data? and set token in header
	private int status;
	private String message;
	private Object data;
	private LocalDateTime now;
	private String token;
	private User userDetails;
	private boolean value;

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

	public Response(String message, int status, Object data, LocalDateTime now, User userDetails) {
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

	public Response(LocalDateTime now, String message, int status) {
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

	public Response(String message, int status, Object data, String tok) {
		this.message = message;
		this.status = status;
		this.data = data;
		this.token = tok;
	}
	public Response(String message, boolean value) {
		this.message = message;
		this.value = value;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + "]";

	}
}