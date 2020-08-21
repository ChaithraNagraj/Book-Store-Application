package com.bridgelabz.bookstore.repo;


import org.hibernate.query.Query;

import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.User;

public interface AddressRepo {

	boolean save(Address add);

	boolean save(User user);
	Object findAddressByType(String addressType, long userId);
	
	public Address findAddressById(long addressId);

//	Address findAddressByTypee(String addressType, long userId);

}

