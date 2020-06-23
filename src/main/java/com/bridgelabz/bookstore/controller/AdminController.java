package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
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

	@GetMapping("/getAllSellers")
	public List<User> getAllSellers() {
		return adminService.getSellers();
	}
	
	@GetMapping("/getAllBuyers")
	public List<User> getAllBuyers() {
		return adminService.getBuyers();
	}
	
	@GetMapping("/getAllBooks")
	public List<Book> getAllBooks() {
		return adminService.getAllBooks();
	}
	
	@GetMapping("/getBooksForVerification")
	public List<Book> getBooksForVerification() {
		return adminService.getBooksForVerification();
	}
	
	@PutMapping("/bookVerification/{bookId}/{sellerId}")
	public void bookVerification(@PathVariable("bookId") Long bookId, @PathVariable("sellerId") Long sellerId, @RequestParam("verify") String verify) throws BookException {
		adminService.bookVerification(bookId, sellerId, verify);
	}
}
