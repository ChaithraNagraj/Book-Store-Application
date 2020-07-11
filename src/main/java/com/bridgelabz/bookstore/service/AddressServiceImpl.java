package com.bridgelabz.bookstore.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.AddressTypeExistsException;
import com.bridgelabz.bookstore.model.Address;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.AddressDTO;
import com.bridgelabz.bookstore.repo.AddressRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {


	@Autowired
	UserRepo userRepo;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	private TokenUtility tokenUtility;
	

	public AddressServiceImpl() {
		System.out.println("AddressImpl working fine");
	}


	@Override
	public Address addAddress(AddressDTO address, String token) {
		 User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		long userId = user.getId();
		 String type = address.getaddressType();
		System.out.println(address);
//		Address add2 = addressRepo.findAddressByType(type, user.getId());
		Address add = new Address();
//            if(!address.getaddressType().isEmpty())
if(address.getAddress()!=null)
            { 	
			BeanUtils.copyProperties(address, add);
			add.setUser(user);
			user.getAddress().add(add);
			addressRepo.save(add);
			return add;
            }else {
            	throw new AddressTypeExistsException("select anyOne addressType mandatorily");
            	}
	}
	
	@Override
	public Address getAddressByType(String addressType, String token) {
		User user = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);

		return addressRepo.findAddressByType(addressType, user.getId());

	}

}
