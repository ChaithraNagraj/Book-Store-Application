package com.bridgelabz.bookstore.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = { "/users" })
//@Api(value = "User Controller")
//@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepository;

	@PostMapping(value = "/register", headers = "Accept=application/json")
	public ResponseEntity<Response> register(@RequestBody @Valid RegistrationDTO request) throws IOException, UserException {
		if (userService.registerUser(request)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.USER_REGISTER_SUCESSFULLY, Constant.OK_RESPONSE_CODE));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.USER_REGISTER_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
		}
	}

	@GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> userVerification(@RequestParam("token") String token) throws UserException {
		if (userService.verify(token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(DateUtility.today(),
					Constant.USER_VERIFIED_SUCCESSFULLY_MEAASGE, Constant.OK_RESPONSE_CODE));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(DateUtility.today(),
					Constant.USER_VERIFIED_FAILD_MEAASGE, Constant.BAD_REQUEST_RESPONSE_CODE));
		}
	}

	@PostMapping(value = "/login", headers = "Accept=application/json")
	public ResponseEntity<Response> userLogin(@RequestBody LoginDTO loginDto) throws UserException {
		if (userService.login(loginDto)) {
			User user = userRepository.getusersByLoginId(loginDto.getloginId());
			String token = JwtValidate.createJWT(user.getId(), loginDto.getRole(), Constant.LOGIN_EXP);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LOGIN_SUCCESSFULL_MESSAGE, Constant.OK_RESPONSE_CODE, user, token));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.LOGIN_FAILED_MESSAGE,
					Constant.BAD_REQUEST_RESPONSE_CODE, DateUtility.today()));
		}
	}

	@PutMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws UserException {
		if (userService.forgetPassword(email)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.CHECK_MAIL_MESSAGE, Constant.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordDto resetPassword,
			@RequestParam("token") String token) throws UserException {
		if (userService.resetPassword(resetPassword, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.PASSWORD_UPTATION_SUCCESSFULLY_MESSAGE, Constant.CREATED_RESPONSE_CODE));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(Constant.VALID_INPUT_MESSAGE, Constant.USER_AUTHENTICATION_EXCEPTION_STATUS));
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<User> getAllUser() {
		return userService.getUser();
	}

	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<String> updateUser(@RequestBody User currentUser) {
		User user = userService.findById(currentUser.getId());
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.update(currentUser, currentUser.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", headers = "Accept=application/json")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/roles", headers = "Accept=application/json")
	public ResponseEntity<Void> addRole(@RequestBody RoleDTO request) {
		HttpHeaders headers = new HttpHeaders();
		if (userService.addRole(request))
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		return new ResponseEntity<>(headers, HttpStatus.ALREADY_REPORTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<Response> logOut(@RequestParam("token") String token) throws UserException {
		if (userService.logOut(token)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LOGOUT_MEAASGE, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.LOGOUT_FAILED_MEAASGE, Constant.OK_RESPONSE_CODE));
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<Response> uploadFile(@RequestPart("file") MultipartFile file, @RequestHeader String token,
			@RequestParam("isProfile") String isProfile) {
		String imageUrl = userService.uploadFile(file, token, isProfile);
		if (imageUrl != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.PROFILE_IMAGE_UPLOADED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE, imageUrl));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.PROFILE_IMAGE_UPLOADED_FAILED, Constant.OK_RESPONSE_CODE));
		}
	}

	@DeleteMapping("/deleteimage")
	public ResponseEntity<Response> deleteFile(@RequestParam("url") String fileUrl,@RequestHeader("token") String token,@RequestParam("isProfile") String isProfile) {
		if (userService.deleteFileFromS3Bucket(fileUrl,token,isProfile)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.PROFILE_IMAGE_DELETED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.PROFILE_IMAGE_DELETED_FAILED, Constant.OK_RESPONSE_CODE));
		}

	}

	@PutMapping("/update")
	public ResponseEntity<Response> userUpdate(@RequestParam("userName") String userName,
			@RequestParam("password") String password, @RequestParam("token") String token) throws UserException {
		if (userService.updateUser(userName, password, token)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.USER_DETAILS_UPDATED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.USER_DETAILS_UPDATED_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

}
