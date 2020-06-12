package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	@NotEmpty(message = "Enter First Name - Registration DTO")
	@Size(min = 3)
	private String name;

	@NotEmpty(message = "Enter User Name - Registration DTO")
	@Size(min = 3)
	private String userName;

	@NotEmpty(message = "Enter Email ID - Registration DTO")
	@Email
	private String email;

	@NotEmpty(message = "Enter Password - Registration DTO")
	@Size(min = 3)
	private String password;

	@NotEmpty(message = "Enter City - Registration DTO")
	private Long moblieNumber;

	@NotEmpty(message = "Enter Role - Registration DTO")
	private String role;

	public RegistrationDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public RegistrationDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public Long getMoblieNumber() {
		return moblieNumber;
	}

	public void setMoblieNumber(Long moblieNumber) {
		this.moblieNumber = moblieNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RegistrationDTO [name=" + name + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", moblieNumber=" + moblieNumber + ", role=" + role + "]";
	}

	/*
	 * @Override public String toString() { return "RegistrationDTO [firstName=" +
	 * firstName + ", lastName=" + lastName + ", userName=" + userName + ", email="
	 * + email + ", password=" + password + ", city=" + city + ", moblieNumber=" +
	 * moblieNumber + ", role=" + role + "]"; }
	 */

}
