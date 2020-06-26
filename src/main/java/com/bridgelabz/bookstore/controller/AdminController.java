package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.AdminService;
import com.bridgelabz.bookstore.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	// Kalpesh Review: Always make Autowired obj private.

	@Autowired
	UserService userService;

	@Autowired
	AdminService adminService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<Response> getAllUsers(@RequestHeader("token") String token) {
		List<User> users = userService.getUser();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), users));
	}

	@GetMapping("/getAllBuyers")
	public ResponseEntity<Response> getAllBuyers(@RequestHeader("token") String token) {

		List<User> buyers = userService.getUser();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), buyers));
	}

	@GetMapping("/getAllSellers")
	public ResponseEntity<Response> getAllSellers(@RequestHeader("token") String token) {

		List<User> sellers = userService.getUser();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("User Found", HttpStatus.OK.value(), sellers));
	}

	@GetMapping("/getAllBooks")
	public ResponseEntity<Response> getAllBooks(@RequestHeader("token") String token) {

		List<Book> books = adminService.getAllBooks();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.BOOK_FOUND, HttpStatus.OK.value(), books));
	}

	@GetMapping("/getBooksForVerification")
	public ResponseEntity<Response> getBooksForVerification(@RequestHeader("token") String token) {

		List<Book> books = adminService.getBooksForVerification();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.BOOK_FOUND, HttpStatus.OK.value(), books));
	}

	@PutMapping("/bookVerification/{bookId}/{sellerId}")
	public ResponseEntity<Response> bookVerification(@RequestHeader("token") String token,
			@PathVariable("bookId") Long bookId, @PathVariable("sellerId") Long sellerId,
			@RequestParam("verify") String verify) throws BookException {
		adminService.bookVerification(bookId, sellerId, verify);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Verification done", HttpStatus.OK.value()));
	}
}
