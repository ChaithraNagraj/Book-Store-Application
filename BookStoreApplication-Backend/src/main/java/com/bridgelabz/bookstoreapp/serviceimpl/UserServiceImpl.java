package com.bridgelabz.bookstoreapp.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.constants.Constant;
import com.bridgelabz.bookstoreapp.dto.LoginDto;
import com.bridgelabz.bookstoreapp.exception.UserNotFoundException;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.response.Response;
import com.bridgelabz.bookstoreapp.service.UserService;
import com.bridgelabz.bookstoreapp.utility.JwtValidate;
import com.bridgelabz.bookstoreapp.utility.RedisCache;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RedisCache<Object> redis;

	@Autowired
	private UserRepository userRepository;

	private String redisKey = "Key";

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public ResponseEntity<Response> verify(String token) {
		long id = JwtValidate.decodeJWT(token);
		User idAvailable = userRepository.findById(id);
		if (idAvailable == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE));
		} else {
			if (!idAvailable.isVerified()) {
				idAvailable.setVerified(true);
				userRepository.verify(idAvailable.getUserId());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(Constant.USER_VERIFIED_SUCCESSFULLY_MEAASGE, Constant.OK_RESPONSE_CODE));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new Response(Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS));
		}
	}

	@Override
	public ResponseEntity<Response> login(LoginDto logindto) throws UserNotFoundException {
		User user = userRepository.findEmail(logindto.getloginId());
		if (logindto.getloginId().equalsIgnoreCase(user.getEmailId())
				|| logindto.getloginId().equalsIgnoreCase(user.getUserName())
				|| logindto.getloginId().equalsIgnoreCase(user.getMobileNumber())
						&& bCryptPasswordEncoder.matches(logindto.getPassword(), user.getPassword())) {
			String token = JwtValidate.createJWT(user.getUserId(), Constant.LOGIN_EXP);
			user.setUpdatedDateTime(Constant.LOCAL_DATE_TIME);
			userRepository.save(user);
			logger.info("Token: " + token);
			redis.putMap(redisKey, user.getEmailId(), token);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.LOGIN_SUCCESSFULL_MESSAGE, Constant.OK_RESPONSE_CODE));
		}

		throw new UserNotFoundException(Constant.LOGIN_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
	}

}
