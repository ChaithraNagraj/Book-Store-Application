package com.bridgelabz.bookstoreapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstoreapp.dto.LoginDto;
import com.bridgelabz.bookstoreapp.exception.UserNotFoundException;
import com.bridgelabz.bookstoreapp.response.Response;

@Component
public interface UserService {

	ResponseEntity<Response> login(LoginDto logindto) throws UserNotFoundException;
	ResponseEntity<Response> verify(String token);
}
