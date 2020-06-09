package com.bridgelabz.bookstoreapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapp.dto.LoginDto;
import com.bridgelabz.bookstoreapp.exception.UserNotFoundException;
import com.bridgelabz.bookstoreapp.response.Response;
import com.bridgelabz.bookstoreapp.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userservice;

	@GetMapping("/verify")
	public ResponseEntity<Response> userVerification(@RequestParam("token") String token) {
		return userservice.verify(token);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody LoginDto logindto) throws UserNotFoundException {
		return userservice.login(logindto);
	}
}