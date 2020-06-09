package com.bridgelabz.bookstoreapp.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginDto {

	private String loginId;
	private String password;

	public String getloginId() {
		return loginId;
	}

	public void setloginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
