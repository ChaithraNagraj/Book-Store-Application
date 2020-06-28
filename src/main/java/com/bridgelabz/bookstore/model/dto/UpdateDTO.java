package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateDTO {
	// Kalpesh Review: password is not mandatory
	// user can't update userName once define because it is unique

//	@Size(min = 3)
//	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "UserName Should be One Special Character,Numbers and One UpperCase")
//	private String userName;

	@Size(min = 8)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;
	
	private Long mobileNumber;

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

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
