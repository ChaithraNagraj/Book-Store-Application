package com.bridgelabz.bookstore.model.dto;

import org.springframework.stereotype.Component;

@Component
public class RoleDTO {

	// Kalpesh Review: convert role into upper case in dto level so we can remove
	// Unnecessary code from service layer
	private String role;

	public RoleDTO() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
