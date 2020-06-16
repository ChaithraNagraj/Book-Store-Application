package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.response.Response;

public interface UserService {
	public ResponseEntity<Response> login(LoginDTO logindto) throws UserNotFoundException;

	public ResponseEntity<Response> verify(String token);

	public ResponseEntity<Response> forgetPassword(String email) throws UserException;

	public ResponseEntity<Response> resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

	public ResponseEntity<Response> registerUser(RegistrationDTO user) throws IOException, UserException;

	public User findById(Long id);

	public List<User> getUser();

	public User update(User user, Long id);

	public void deleteUserById(Long id);

	public boolean addRole(RoleDTO request);
	
	ResponseEntity<Response> logOut(String token) throws UserException;

	boolean isSessionActive(String token);
}
