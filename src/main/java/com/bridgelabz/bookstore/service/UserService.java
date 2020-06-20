package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;


import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;

public interface UserService {
	public boolean login(LoginDTO logindto) throws UserNotFoundException, UserException;

	public boolean verify(String token) throws UserException;

	public boolean forgetPassword(String email) throws UserException;

	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

	public int registerUser(RegistrationDTO user) throws IOException, UserException;

	public User findById(Long id);

	public List<User> getUser();

	public User update(User user, Long id);

	public void deleteUserById(Long id);

	public boolean addRole(RoleDTO request);
	
	public boolean logOut(String token) throws UserException;

	boolean isSessionActive(String token);
}
