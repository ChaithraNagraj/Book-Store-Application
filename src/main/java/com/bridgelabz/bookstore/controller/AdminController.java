package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.service.UserService;



@RestController
public class AdminController {
	@Autowired
	UserService userService;
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userService.getUser();
	}
	@GetMapping("/getAllBuyers")
	public void getAllBuyers() {
		
	}
	@GetMapping("/getAllSellers")
	public void getAllSellers() {
		
	}
	@GetMapping("/getAllBooks")
	public void getAllBooks() {
		
	}
}
