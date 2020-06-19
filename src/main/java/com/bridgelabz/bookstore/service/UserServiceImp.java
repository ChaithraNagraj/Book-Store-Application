package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.repo.RoleRepositoryImp;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.RedisCache;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
@Component
public class UserServiceImp implements UserService {

	@Autowired
	private RoleRepositoryImp roleRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private MailTempletService mailTempletService;

	@Autowired
	private WebSecurityConfig encrypt;

	@Autowired
	private RedisCache<Object> redis;

	@Autowired
	private Environment environment;

	private String redisKey = "Key";

	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	public ResponseEntity<Response> registerUser(RegistrationDTO userDetails) throws UserException {
		User userEntity = userRepository.getusersByemail(userDetails.getEmail());
		if (userEntity != null) {
			throw new UserException(Constant.USER_ALREADY_REGISTER_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
		} else {
			User user = new User();
			BeanUtils.copyProperties(userDetails, user);
			user.setFullName(userDetails.getName());
			user.setPassword(encrypt.bCryptPasswordEncoder().encode(userDetails.getPassword()));
			user.setRegistrationDateTime(DateUtility.today());
			user.setUpdateDateTime(DateUtility.today());
			user.setMobileNumber(userDetails.getMobileNumber());
			user.setVerify(false);
			if (userDetails.getRole().equals("1")) {
				Role roleEntity = roleRepository.getRoleByName("Buyer");
				roleEntity.getUser().add(user);
				roleRepository.save(roleEntity);
			}
			if (userDetails.getRole().equals("2")) {
				Role roleEntity = roleRepository.getRoleByName("Seller");
				roleEntity.getUser().add(user);
				roleRepository.save(roleEntity);
			}
			if (userDetails.getRole().equals("3")) {
				Role roleEntity = roleRepository.getRoleByName("buyer");
				roleEntity.getUser().add(user);
				roleRepository.save(roleEntity);
				roleEntity = roleRepository.getRoleByName("Seller");
				roleEntity.getUser().add(user);
				roleRepository.save(roleEntity);
			}
			registerMail(user, environment.getProperty("registration-template-path"));
			System.out.println(user);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.USER_REGISTER_SUCESSFULLY,
					Constant.OK_RESPONSE_CODE, user, DateUtility.today()));

		}
	}

	private Role getRoleName(String userRole) {
		return userRepository.findByRoleId(Long.parseLong(userRole));
	}

	private void registerMail(User user, String templet) {
		String token = TokenUtility.verifyResponse(user.getId());
		sendMail(user, token, templet);
	}

	private void sendMail(User user, String token, String templet) {
		try {
			mailTempletService.getTemplate(user, token, templet);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

	public User findById(Long id) {
		return userRepository.findByUserId(id);
	}

	public List<User> getUser() {
		return userRepository.getUser();
	}

	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}

	public User update(User user, Long id) {
		return userRepository.update(user, id);
	}

	public ResponseEntity<Response> verify(String token) {
		long id = JwtValidate.decodeJWT(token);
		User idAvailable = userRepository.findByUserId(id);
		if (idAvailable == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(DateUtility.today(),
					Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
		} else {
			if (!idAvailable.isVerify()) {
				idAvailable.setVerify(true);
				userRepository.verify(idAvailable.getId());
				registerMail(idAvailable, environment.getProperty("login-template-path"));
				return ResponseEntity.status(HttpStatus.OK).body(new Response(DateUtility.today(),
						Constant.USER_VERIFIED_SUCCESSFULLY_MEAASGE, Constant.OK_RESPONSE_CODE));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(DateUtility.today(),
					Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS));
		}
	}

	public ResponseEntity<Response> login(LoginDTO loginDto) throws UserNotFoundException {
		User user = userRepository.getusersByemail(loginDto.getloginId());
		if (encrypt.bCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword()) && user.isVerify()) {
			String token = JwtValidate.createJWT(user.getId(), Constant.LOGIN_EXP);
			userRepository.updateDateTime(user.getId());
			user.setUpdateDateTime(DateUtility.today());
			userRepository.updateUserStatus(Boolean.TRUE, user.getId());
			redis.putMap(redisKey, user.getEmail(), token);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.LOGIN_SUCCESSFULL_MESSAGE,
					Constant.OK_RESPONSE_CODE, token, DateUtility.today(), user));
		}
		throw new UserNotFoundException(Constant.LOGIN_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
	}

	public boolean addRole(RoleDTO request) {
		request.setRole(request.getRole().toUpperCase());
		userRepository.saveRoles(new Role(request));
		return true;
	}

	public ResponseEntity<Response> forgetPassword(String email) throws UserException {
		User maybeUser = userRepository.getusersByemail(email);
		if (maybeUser != null && maybeUser.isVerify()) {
			registerMail(maybeUser, environment.getProperty("forgot-password-template-path"));
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.CHECK_MAIL_MESSAGE, Constant.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	public ResponseEntity<Response> resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
		if (resetPassword.getPassword().equals(resetPassword.getConfirmpassword())) {
			long id = JwtValidate.decodeJWT(token);
			User user = userRepository.findByUserId(id);
			if (user != null) {
				userRepository.updatePassword(user.getId(),
						encrypt.bCryptPasswordEncoder().encode(resetPassword.getConfirmpassword()));
				String getToken = JwtValidate.createJWT(user.getId(), Constant.LOGIN_EXP);
				redis.putMap(redisKey, user.getEmail(), getToken);
				return ResponseEntity.status(HttpStatus.OK).body(
						new Response(Constant.PASSWORD_UPTATION_SUCCESSFULLY_MESSAGE, Constant.CREATED_RESPONSE_CODE));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.VALID_INPUT_MESSAGE, Constant.USER_AUTHENTICATION_EXCEPTION_STATUS));
	}

	@Override
	public boolean isSessionActive(String token) {
		long id = JwtValidate.decodeJWT(token);
		User user = userRepository.findByUserId(id);
		return user.isUserStatus();
	}

	@Override
	public ResponseEntity<Response> logOut(String token) throws UserException {
		long id = JwtValidate.decodeJWT(token);
		User user = userRepository.findByUserId(id);
		if (user == null) {
			throw new UserException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE);
		}
		if (user.isUserStatus()) {
			user.setUserStatus(Boolean.FALSE);
			userRepository.addUser(user);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LOGOUT_MEAASGE, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.LOGOUT_FAILED_MEAASGE, Constant.OK_RESPONSE_CODE));

	}

}
