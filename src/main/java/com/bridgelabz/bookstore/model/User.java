
package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "full_name", nullable = false)
	@Size(min = 3)
	private String name;

	@Column(name = "username", unique = true, nullable = false)
	private String userName;

	@Column(name = "email", unique = true, nullable = false)
	@Email
	private String email;

	@Column(name = "password", nullable = false)
	@Size(min = 3)
	private String password;

	@Column(name = "mobile_number", unique = true, length = 10)
	@NotNull
	private Long mobileNumber;

	@Column(name = "is_verify", columnDefinition = "boolean default false")
	@NotNull
	private boolean isVerify;

	@Column(name = "registration_date_time")
	@NotNull
	private LocalDateTime registrationDateTime;

	@Column(name = "update_date_time")
	@NotNull
	private LocalDateTime updateDateTime;

	@Column(name = "user_status", columnDefinition = "boolean default false")
	@NotNull
	private boolean userStatus;

	@Column(name = "imageUrl")
	private String imageUrl;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "User_Role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	public List<Role> roleList;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Book> sellerBooks;

	// Kalpesh Review: Bad practice to pass more than 4 parameters
	// make object and then pass it
	// and validation is inside call not at parameter level

	public User(Long id, @Size(min = 3) String fullName, String userName, @Email String email,
			@Size(min = 3) String password, @NotNull Long mobileNumber, @NotNull boolean isVerify,
			@NotNull LocalDateTime registrationDateTime, @NotNull LocalDateTime updateDateTime,
			@NotNull boolean userStatus, String imageUrl, List<Role> roleList, List<Book> sellbookList) {
		super();
		this.id = id;
		this.name = fullName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.isVerify = isVerify;
		this.registrationDateTime = registrationDateTime;
		this.updateDateTime = updateDateTime;
		this.userStatus = userStatus;
		this.imageUrl = imageUrl;

		this.roleList = roleList;
		this.sellerBooks = sellbookList;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Book> getSellerBooks() {
		return sellerBooks;
	}

	public void setSellerBooks(List<Book> sellerBooks) {
		this.sellerBooks = sellerBooks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + name + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", mobileNumber=" + mobileNumber + ", isVerify=" + isVerify + ", registrationDateTime="
				+ registrationDateTime + ", updateDateTime=" + updateDateTime + ", userStatus=" + userStatus
				+ ", imageUrl=" + imageUrl + ", roleList=" + roleList + ", books=" + sellerBooks + "]";
	}

}
