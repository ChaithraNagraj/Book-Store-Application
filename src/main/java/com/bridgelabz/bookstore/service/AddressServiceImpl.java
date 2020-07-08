package com.bridgelabz.bookstore.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.AddressDTO;
import com.bridgelabz.bookstore.repo.AddressRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

<<<<<<< HEAD
	public AddressServiceImpl() {
		System.out.println("constructor of AddressServiceImpl is started workingg");
	}

=======
>>>>>>> 9359c64502ab54007281976edc5eb609dea5fe47
	@Autowired
	UserRepo userRepo;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	private TokenUtility tokenUtility;

	@Override
	public Address addAddress(AddressDTO address, String token) {
<<<<<<< HEAD
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		long userId = user.getId();
		String type = address.getaddressType();
		Address add2 = addressRepo.findAddressByType(type, user.getId());
		Address add = new Address();
		if (add2 != null) {
			add2.setAddress(address.getAddress());
			add2.setAddressType(address.getaddressType());
			add2.setCity(address.getCity());
			add2.setLandmark(address.getLandmark());
			add2.setLocality(address.getLocality());
			add2.setName(address.getName());
			add2.setPhoneNumber(address.getPhoneNumber());
			add2.setPincode(address.getPincode());
			add2.setUser(user);
			user.getAddress().add(add2);
			addressRepo.save(add2);
			return add2;
		} else {
=======

		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		String type = address.getaddressType();
		Address add2 = addressRepo.findAddressByType(type, user.getId());
		Address add = new Address();

		if (add2 != null) {
			return add2;
		} else {

>>>>>>> 9359c64502ab54007281976edc5eb609dea5fe47
			BeanUtils.copyProperties(address, add);
			add.setUser(user);
			user.getAddress().add(add);
			addressRepo.save(add);
			return add;
		}

	}
<<<<<<< HEAD
	
=======
>>>>>>> 9359c64502ab54007281976edc5eb609dea5fe47

	@Override
	public Address getAddressByType(String addressType, String token) {
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
<<<<<<< HEAD
		long userId = user.getId();
		Address address = addressRepo.findAddressByType(addressType, userId);
		System.out.println("printing address from addressServiceimpl");
		System.out.println(address);
		return address;
=======
		return addressRepo.findAddressByType(addressType, user.getId());

>>>>>>> 9359c64502ab54007281976edc5eb609dea5fe47
	}

}
