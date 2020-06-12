package com.bridgelabz.bookstore.response;
import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class MailResponse implements Serializable{

private static final long serialVersionUID = 1L;
	
	String email;
    String subject;
	String message;
	
	
	
	public MailResponse() {
		super();
	}

	public MailResponse(String email, String subject, String message) {
		super();
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}