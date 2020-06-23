package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.response.Response;
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
	public ResponseEntity<Response> getAllUsers() {
		if(!userService.getUser().isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), userService.getUser()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.value()));
	}

	@GetMapping("/getAllBuyers")
	public ResponseEntity<Response> getAllBuyers() {
		
		if(!adminService.getBuyers().isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), adminService.getBuyers()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.value()));	
	}


	@GetMapping("/getAllSellers")
	public ResponseEntity<Response> getAllSellers() {
		
		if(!adminService.getSellers().isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), adminService.getSellers()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, HttpStatus.NOT_FOUND.value()));
	}
	

	
	@GetMapping("/getAllBooks")
	public ResponseEntity<Response> getAllBooks() {
		
		if(!adminService.getAllBooks().isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_FOUND, HttpStatus.OK.value(), adminService.getAllBooks()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Constant.BOOK_NOT_FOUND, HttpStatus.NOT_FOUND.value()));	
	}
	
	@GetMapping("/getBooksForVerification")
	public ResponseEntity<Response> getBooksForVerification() {
		
		if(!adminService.getBooksForVerification().isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_FOUND, HttpStatus.OK.value(), adminService.getBooksForVerification()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(Constant.BOOK_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
	}
	
	@PutMapping("/bookVerification/{bookId}/{sellerId}")
	public ResponseEntity<Response> bookVerification(@PathVariable("bookId") Long bookId, @PathVariable("sellerId") Long sellerId, @RequestParam("verify") String verify) throws BookException {
		adminService.bookVerification(bookId, sellerId, verify);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Verification done", HttpStatus.OK.value()));
	}
}
