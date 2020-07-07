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
	
	public AddressServiceImpl() {
		System.out.println("constructor of AddressServiceImpl is started workingg");
	}
	@Autowired
	UserRepo userRepo;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	private TokenUtility tokenUtility;
	@Override
	public Address addAddress(AddressDTO address, String token) {
		System.out.println("Adress service impl started");
//		Long id = generate.parseJWT(token);
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		System.out.println("printing the user");
		System.out.println(user);
//		cart.setUser(buyer);
		
		Address add=new Address();
		BeanUtils.copyProperties(address,add);
		add.setUser(user);
		user.getAddress().add(add);
		addressRepo.save(add);
		
		return add;

		


//		System.out.println(add.getAddress()+"->"+add.getCity());
////		Users userdetails = userRepo.findById(id)
////				.orElseThrow(null);
//		user.getAddress().add(add);
//		return   addressRepository.save(add);
		
	}
	@Override
	public Address getAddressByType(String addressType, String token) {
//		Long id = generate.parseJWT(token);
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
	    long userId=user.getId();

		Address address=addressRepo.findAddressByType(addressType,userId);
		System.out.println("printing address from addressServiceimpl");
		System.out.println(address);
		return address;
	}

}

