package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateDTO {
	
	@Size(min = 3)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "UserName Should be One Special Character,Numbers and One UpperCase")
	private String fullName;
	@Size(min = 8)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;
	//private Long mobileNumber;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	
}
