package com.bridgelabz.bookstore.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.model.dto.UpdateDTO;

public interface UserService {
	public boolean login(LoginDTO logindto) throws UserNotFoundException, UserException;

	public boolean verify(String token) throws UserException;

	public boolean forgetPassword(String email) throws UserException;

	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

	public boolean registerUser(RegistrationDTO user) throws IOException, UserException;

	public User findById(Long id);

	public List<User> getUser();

	public void deleteUserById(Long id);

	public boolean addRole(RoleDTO request);

	public boolean logOut(String token) throws UserException;

	public boolean isSessionActive(String token);

	public boolean updateUser(UpdateDTO updateDTO, String token) throws UserException;

	public String uploadFileTos3bucket(String fileName, File file, String isProfile);

	public boolean deleteFileFromS3Bucket(String fileUrl, String token, String isProfile);

	public String uploadFile(MultipartFile multipartFile, String token, String isProfile);

}
