package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.service.AdminService;
import com.bridgelabz.bookstore.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserService userService;

	@Autowired
	AdminService adminService;

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userService.getUser();
	}

	@GetMapping("/getAllBuyers")
	public List<User> getAllBuyers() {
		return adminService.getBuyers();
	}

	@GetMapping("/getAllSellers")
	public List<User> getAllSellers() {
		return adminService.getSellers();
	}

	@GetMapping("/getAllBooks")
	public void getAllBooks() {

	}
}
