package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateDTO {

	@NotEmpty(message = "Enter First Name - Registration DTO")
	@Size(min = 3)
	@Pattern(regexp = "^[A-Z][a-z]+\\s?[A-Z][a-z]+$", message = "Please Enter Valid FirstName")
	private String fullName;

	@Size(min = 8)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;

	public String getUserName() {
		return fullName;
	}

	public void setUserName(String fullName) {
		this.fullName = fullName;
	}	
	private Long mobileNumber;


	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
