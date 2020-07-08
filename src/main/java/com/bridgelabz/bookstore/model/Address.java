package com.bridgelabz.bookstore.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private long addressId;

	@Column(name = "customer_pincode")
	private String pincode;

	@Column(name = "customer_locality")
	private String locality;

	@Column(name = "customer_address")
	private String address;

	@Column(name = "customer_city")
	private String city;

	@Column(name = "customer_landmark")
	private String landmark;
	
	@Column(name = "country")
    private String country;
	
	@Column(name = "address_type")
    private String addressType;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String name;
	
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address() {
		super();
	}

	public Address(long addressId, String pincode, String locality, String address, String city, String landmark,
			String country, String addressType, String phoneNumber, String name, User user) {
		
		this.addressId = addressId;
		this.pincode = pincode;
		this.locality = locality;
		this.address = address;
		this.city = city;
		this.landmark = landmark;
		this.country = country;
		this.addressType = addressType;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.user = user;
	}
	
	

}

