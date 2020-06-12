package com.bridgelabz.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.RedisCache;

@Service
@Component
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private WebSecurityConfig encrypt;

	@Autowired
	private RedisCache<Object> redis;

	private String redisKey = "Key";

	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Override
	public boolean registerUser(RegistrationDTO user) {
		User maybeUser = userRepo.getusersByemail(user.getEmail());
		System.out.println("regDto" + user);
		Role role = new Role();
		role.setRole(user.getRole());

		logger.info("UserDetails: " + maybeUser);
		if (maybeUser != null) {
			System.out.println("hi null");
			return false;
		}
		System.out.println("hi not null");

		// userRepo.addUser(new
		// User(user.setPassword(encrypt.bCryptPasswordEncoder().encode(user.getPassword()))));
//		userRepo.addUser(new User(user));
		User u = new User(user);
		u.setPassword(encrypt.bCryptPasswordEncoder().encode(user.getPassword()));
		Role r = getRoleName(user.getRole());
		u.setRole(r.getRole());
		userRepo.addUser(u);
		// User isUser = userRepo.getusersByemail(user.getEmail());
		// logger.info("UserDetails: " + isUser);
		// String response = Constant.VERIFY_ADDRESS +
		// JwtValidate.createJWT(isUser.getId(), Constant.REGISTER_EXP);
		// rabbitMqSender.send(new MailResponse(isUser.getEmail(),
		// Constant.VERIFICATION, response));
		return true;
	}

	private Role getRoleName(String role) {
		return userRepo.findByRoleId(Long.parseLong(role));
	}

	@Override
	public User findById(Long id) {
		return userRepo.findByUserId(id);
	}

	@Override
	public List<User> getUser() {
		return userRepo.getUser();
	}

	@Override
	public void deleteUserById(Long id) {
		userRepo.delete(id);
	}

	@Override
	public User update(User user, Long id) {
		return userRepo.update(user, id);
	}

	@Override
	public ResponseEntity<Response> verify(String token) {
		long id = JwtValidate.decodeJWT(token);
		User idAvailable = userRepo.findByUserId(id);
		if (idAvailable == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
		} else {
			if (!idAvailable.isVerify()) {
				idAvailable.setVerify(true);
				userRepo.verify(idAvailable.getId());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(Constant.USER_VERIFIED_SUCCESSFULLY_MEAASGE, Constant.OK_RESPONSE_CODE));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new Response(Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS));
		}
	}

	@Override
	public ResponseEntity<Response> login(LoginDTO logindto) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRole(RoleDTO request) {
		request.setRole(request.getRole().toUpperCase());
		userRepo.saveRoles(new Role(request));
		return true;
	}

}
