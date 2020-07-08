package com.bridgelabz.bookstore.repo;


import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.User;

public interface AddressRepo {

	boolean save(Address add);

	boolean save(User user);

	Address findAddressByType(String addressType, long userId);

}

