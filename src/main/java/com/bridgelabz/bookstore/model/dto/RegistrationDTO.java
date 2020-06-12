package com.bridgelabz.bookstore.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	@NotEmpty(message = "Enter First Name - Registration DTO")
	@Size(min = 3)
	@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName")
	private String firstName;

	@NotEmpty(message = "Enter Last Name - Registration DTO")
	@Size(min = 3)
	@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName")
	private String lastName;

	@NotEmpty(message = "Enter Email ID - Registration DTO")
	@Email
	private String email;

	@NotEmpty(message = "Enter Password - Registration DTO")
	@Size(min = 3)
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String password;

	@NotEmpty(message = "Enter City - Registration DTO")
	private String city;

	@NotEmpty(message = "Enter City - Registration DTO")
	@Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Please Enter Valid PhoneNumber")
	private Long moblieNumber;

	public RegistrationDTO() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getMoblieNumber() {
		return moblieNumber;
	}

	public void setMoblieNumber(Long moblieNumber) {
		this.moblieNumber = moblieNumber;
	}

}
