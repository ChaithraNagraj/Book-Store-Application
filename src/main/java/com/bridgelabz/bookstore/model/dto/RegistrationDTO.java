package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	@NotEmpty(message = "Enter First Name - Registration DTO")
	//@Size(min = 3)
	//@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName")
	private String name;

	//@Size(min = 3)
	//@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid UserName")
	private String userName;

	@Email
	private String email;

//	@Size(min = 3)
//	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;

	@NotEmpty(message = "Enter Role - Registration DTO")
	private String role;

	//@Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Please Enter Valid PhoneNumber")
	private Long moblieNumber;

	public RegistrationDTO() {
		super();
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



}
