package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.response.Response;
@Component
public interface UserService {
	ResponseEntity<Response> login(LoginDTO logindto) throws UserNotFoundException;

	ResponseEntity<Response> verify(String token);

	ResponseEntity<Response> forgetPassword(String email) throws UserException;

	ResponseEntity<Response> resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

	public boolean registerUser(RegistrationDTO user) throws IOException;

	public User findById(Long id);

	public List<User> getUser();

	public User update(User user, Long id);

	public void deleteUserById(Long id);

	boolean addRole(RoleDTO request);
}
