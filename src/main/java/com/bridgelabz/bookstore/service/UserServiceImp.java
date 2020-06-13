package com.bridgelabz.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User.UserBuilder;
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
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.RabbitMQSender;
import com.bridgelabz.bookstore.utils.RedisCache;

@Service
@Component
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private MailTempletService mailTempletService;

	@Autowired
	private RabbitMQSender rabbitMqSender;

	@Autowired
	private WebSecurityConfig encrypt;

	@Autowired
	private RedisCache<Object> redis;

	private String redisKey = "Key";

	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Override
	public ResponseEntity<Response> registerUser(RegistrationDTO user) throws UserException {
		List<User> maybeUser = userRepository.findByEmail(user.getEmail());
		logger.info("UserDetails: " + maybeUser);
		if (maybeUser != null) {
			User u = new User(user);
			logger.info("UserDetails: " + u.getEmail());
			u.setPassword(encrypt.bCryptPasswordEncoder().encode(user.getPassword()));
			Role r = getRoleName(user.getRole());
			u.setRole(r.getRole());
			userRepository.addUser(u);
//			try {
//				mailTempletService.getTemplate(u);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

//			String response = Constant.VERIFY_ADDRESS + JwtValidate.createJWT(u.getId(), Constant.REGISTER_EXP);
//			rabbitMqSender.send(new MailResponse(u.getEmail(), Constant.VERIFICATION, response));
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.USER_REGISTER_SUCESSFULLY, Constant.OK_RESPONSE_CODE));
		}
		throw new UserException(Constant.USER_ALREADY_REGISTER_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
	}

	private Role getRoleName(String role) {
		return userRepository.findByRoleId(Long.parseLong(role));
	}

	@Override
	public User findById(Long id) {
		return userRepository.findByUserId(id);
	}

	@Override
	public List<User> getUser() {
		return userRepository.getUser();
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}

	@Override
	public User update(User user, Long id) {
		return userRepository.update(user, id);
	}

	@Override
	public ResponseEntity<Response> verify(String token) {
		long id = JwtValidate.decodeJWT(token);
		User idAvailable = userRepository.findByUserId(id);
		if (idAvailable == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
		} else {
			if (!idAvailable.isVerify()) {
				idAvailable.setVerify(true);
				userRepository.verify(idAvailable.getId());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(Constant.USER_VERIFIED_SUCCESSFULLY_MEAASGE, Constant.OK_RESPONSE_CODE));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new Response(Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS));
		}
	}

	@Override
	public ResponseEntity<Response> login(LoginDTO loginDto) throws UserNotFoundException {
		User user = userRepository.getusersByemail(loginDto.getloginId());
		logger.info("given: " + encrypt.bCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword()));
		if (encrypt.bCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
			String token = JwtValidate.createJWT(user.getId(), Constant.LOGIN_EXP);
			userRepository.updateDateTime(user.getId());
			user.setUpdateDateTime(DateUtility.today());
			logger.info("Token: " + token);
			redis.putMap(redisKey, user.getEmail(), token);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LOGIN_SUCCESSFULL_MESSAGE, Constant.OK_RESPONSE_CODE));
		}

		throw new UserNotFoundException(Constant.LOGIN_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
	}

	@Override
	public boolean addRole(RoleDTO request) {
		request.setRole(request.getRole().toUpperCase());
		userRepository.saveRoles(new Role(request));
		return true;
	}

	public ResponseEntity<Response> forgetPassword(String email) throws UserException {

		User isIdAvailable = userRepository.getusersByemail(email);
		if (isIdAvailable != null && isIdAvailable.isVerify()) {
			String response = Constant.RESET_PASSWORD
					+ JwtValidate.createJWT(isIdAvailable.getId(), Constant.LOGIN_EXP);
			// rabbitMqSender.send(new MailResponse(isIdAvailable.getEmail(),
			// Constant.PASSWORD_UPDATE_MESSAGE, response));

			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.CHECK_MAIL_MESSAGE, Constant.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@Override
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
}
