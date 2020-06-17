package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.RoleRepository;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	RoleRepository roleRepository;
	@Override
	public List<User> getBuyers() {

		return roleRepository.getRoleById(2).getUser();
	}

	@Override
	public List<User> getSellers() {

		return roleRepository.getRoleById(3).getUser();
	}	
	
	
}
