package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
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

	public int registerUser(RegistrationDTO userDetails) throws UserException {
		Role role = roleRepository.getRoleById(Integer.parseInt(userDetails.getRole()));
		Optional<User> userEmailExists = Optional.ofNullable(userRepository.getusersByemail(userDetails.getEmail()));
System.out.println("user: "+userEmailExists);
		if (userEmailExists.isPresent()) {
			Optional.ofNullable(userRepository.findByUserIdAndRoleId(userEmailExists.get().getId(),
					Long.parseLong(userDetails.getRole()))).ifPresent(action -> {
						throw new UsernameExistsException("User Already Regsitered As ");
					});
			userEmailExists.get().roleList.add(role);
			userRepository.addUser(userEmailExists.get());
			return 1;

		} else {
			User userEntity = new User();
			userDetails.setPassword(encrypt.bCryptPasswordEncoder().encode(userDetails.getPassword()));
			BeanUtils.copyProperties(userDetails, userEntity);
			userEntity.setRegistrationDateTime(DateUtility.today());
			userEntity.setUpdateDateTime(DateUtility.today());
			userEntity.setMobileNumber(userDetails.getMobileNumber());
			List<Role> roles = new ArrayList<>();
			roles.add(role);
			userEntity.setRoleList(roles);
			userRepository.addUser(userEntity);
			registerMail(userEntity, environment.getProperty("registration-template-path"));
			return 2;

		}

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

	public boolean verify(String token) throws UserException {
		long id = JwtValidate.decodeJWT(token);
		User idAvailable = userRepository.findByUserId(id);
		if (idAvailable == null) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		} else {
			if (!idAvailable.isVerify()) {
				idAvailable.setVerify(true);
				userRepository.verify(idAvailable.getId());
				registerMail(idAvailable, environment.getProperty("login-template-path"));
				return true;

			}
			throw new UserException(Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS);

		}
	}

	public boolean login(LoginDTO loginDto) throws UserException {
		User user = userRepository.getusersByLoginId(loginDto.getloginId());
		User roleWithUser = userRepository.findByUserIdAndRoleId(user.getId(), loginDto.getRole());
		System.out.println(roleWithUser);
		if (roleWithUser != null) {
			if (encrypt.bCryptPasswordEncoder().matches(loginDto.getPassword(), roleWithUser.getPassword())
					&& roleWithUser.isVerify()) {
				String token = JwtValidate.createJWT(user.getId(), Constant.LOGIN_EXP);
				userRepository.updateDateTime(user.getId());
				user.setUpdateDateTime(DateUtility.today());
				userRepository.updateUserStatus(Boolean.TRUE, user.getId());
				redis.putMap(redisKey, user.getEmail(), token);
				return true;
			}
			throw new UserException(Constant.LOGIN_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE);
		}
		throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE);
	}

	public boolean addRole(RoleDTO request) {
		request.setRole(request.getRole().toUpperCase());
		userRepository.saveRoles(new Role(request));
		return true;
	}

	public boolean forgetPassword(String email) throws UserException {
		User maybeUser = userRepository.getusersByemail(email);
		if (maybeUser != null && maybeUser.isVerify()) {
			registerMail(maybeUser, environment.getProperty("forgot-password-template-path"));
			return true;
		}
		return false;

	}

	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
		if (resetPassword.getPassword().equals(resetPassword.getConfirmpassword())) {
			long id = JwtValidate.decodeJWT(token);
			User user = userRepository.findByUserId(id);
			if (user != null) {
				userRepository.updatePassword(user.getId(),
						encrypt.bCryptPasswordEncoder().encode(resetPassword.getConfirmpassword()));
				String getToken = JwtValidate.createJWT(user.getId(), Constant.LOGIN_EXP);
				redis.putMap(redisKey, user.getEmail(), getToken);
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean isSessionActive(String token) {
		long id = JwtValidate.decodeJWT(token);
		User user = userRepository.findByUserId(id);
		return user.isUserStatus();
	}

	@Override
	public boolean logOut(String token) throws UserException {
		long id = JwtValidate.decodeJWT(token);
		User user = userRepository.findByUserId(id);
		if (user == null) {
			throw new UserException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE);
		}
		if (user.isUserStatus()) {
			user.setUserStatus(Boolean.FALSE);
			userRepository.addUser(user);
			return true;
		}
		return false;

	}

}
