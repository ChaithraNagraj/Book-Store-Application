package com.bridgelabz.bookstore.model.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginDTO {
	// Kalpesh Review: Handle all validation at DTO level

	private String loginId;
	private String password;
	private Long role;

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

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public LoginDTO(String loginId, String password, Long role) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.role = role;
	}

	public LoginDTO() {
		super();
	}

}
