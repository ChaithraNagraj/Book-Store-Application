package com.bridgelabz.bookstore.model.dto;

import java.util.regex.Matcher;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	@NotEmpty(message = "Enter First Name - Registration DTO")
	@Size(min = 3)
	//@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName")
	private String name;

	public RegistrationDTO(
			@NotEmpty(message = "Enter First Name - Registration DTO") @Size(min = 3) @Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName") String name,
			@Size(min = 3) @Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid UserName") String userName,
			@Email String email,
			@Size(min = 3) @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number") String password,
			@NotEmpty(message = "Enter Role - Registration DTO") String role, Long mobileNumber) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.mobileNumber = mobileNumber;
	}

	@Size(min = 3)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "UserName Should be One Special Character,Numbers and One UpperCase")
	private String userName;

	@Email
	private String email;

	@Size(min = 3)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;

	@NotEmpty(message = "Enter Role - Registration DTO")
	private String role;


	//@Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Please Enter Valid PhoneNumber")
	private Long mobileNumber;


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

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	
	public static Boolean isValid(Long mobileNumber) {
		String phNo = Long.toString(mobileNumber);
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[7-9]{1}[0-9]{9}");
		Matcher m = pattern.matcher(phNo);
		return m.find();
		
	}

	@Override
	public String toString() {
		return "RegistrationDTO [name=" + name + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", moblieNumber=" + mobileNumber + ", role=" + role + "]";
	}



}
