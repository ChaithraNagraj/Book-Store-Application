package com.bridgelabz.bookstore.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.UserAlreadyRegisteredException;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

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

	@Autowired
	private AmazonS3 amazonS3;


	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	private String redisKey = "Key";

	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	public boolean registerUser(RegistrationDTO userDetails) throws UserException {

		Role role = roleRepository.getRoleById(Integer.parseInt(userDetails.getRole()));
		Optional<User> userEmailExists = Optional.ofNullable(userRepository.getusersByemail(userDetails.getEmail()));
		if (userEmailExists.isPresent()) {
			Optional.ofNullable(userRepository.findByUserIdAndRoleId(userEmailExists.get().getId(),
					Long.parseLong(userDetails.getRole()))).ifPresent(action -> {
						throw new UserAlreadyRegisteredException(
								Constant.USER_ALREADY_REGISTER_MESSAGE + " As " + role.getRole());
					});
			userEmailExists.get().roleList.add(role);
			userRepository.addUser(userEmailExists.get());

			return true;

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
			Map<String, Object> documentMapper = objectMapper.convertValue(userEntity, Map.class);
			IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE,
					String.valueOf(userEntity.getId())).source(documentMapper);
			try {
				client.index(indexRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {

				e.printStackTrace();
			}
			registerMail(userEntity, role, environment.getProperty("registration-template-path"));
			return true;
		}
	}


	private void registerMail(User user, Role role, String templet) {
		String token = TokenUtility.verifyResponse(user.getId(), role.getRoleId());
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
		String text = Long.toString(id);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(Constant.INDEX);
		searchRequest.types(Constant.TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("id"));

		searchSourceBuilder.query(query);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (User) getSearchResult(searchResponse);
	}

	public List<User> getUser() {
		List<User> user =  userRepository.getUser();
		if(user.isEmpty()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		return user;
	}

	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}

	public User update(User user, Long id) {
		return userRepository.update(user, id);
	}

	public boolean verify(String token) throws UserException {
		Long id =  Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		long roleId =  Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("roleId"));
		Role role = roleRepository.getRoleById((int) roleId);
		User idAvailable = userRepository.findByUserId(id);
		if (idAvailable == null) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		} else {
			if (!idAvailable.isVerify()) {
				idAvailable.setVerify(true);
				userRepository.verify(idAvailable.getId());
				registerMail(idAvailable, role, environment.getProperty("login-template-path"));
				return true;
			}
			throw new UserException(Constant.USER_ALREADY_VERIFIED_MESSAGE, Constant.ALREADY_EXIST_EXCEPTION_STATUS);

		}
	}

	public boolean login(LoginDTO loginDto) throws UserException {
		User user = userRepository.getusersByLoginId(loginDto.getloginId());
		User roleWithUser = userRepository.findByUserIdAndRoleId(user.getId(), loginDto.getRole());
		if (roleWithUser != null) {
			if (encrypt.bCryptPasswordEncoder().matches(loginDto.getPassword(), roleWithUser.getPassword())
					&& roleWithUser.isVerify()) {
				String token = JwtValidate.createJWT(user.getId(), loginDto.getRole(), Constant.LOGIN_EXP);
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
			registerMail(maybeUser, maybeUser.getRoleList().get(0),
					environment.getProperty("forgot-password-template-path"));
			return true;
		}
		return false;
	}

	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
		if (resetPassword.getPassword().equals(resetPassword.getConfirmpassword())) {
			Long id =  Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
			Long roleId =  Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("roleId"));
			User user = userRepository.findByUserId(id);
			if (user != null) {
				userRepository.updatePassword(user.getId(),
						encrypt.bCryptPasswordEncoder().encode(resetPassword.getConfirmpassword()));
				String getToken = JwtValidate.createJWT(user.getId(), roleId, Constant.LOGIN_EXP);
				redis.putMap(redisKey, user.getEmail(), getToken);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSessionActive(String token) {
		long id = JwtValidate.decodeJWT(token).get("userId", Long.class);
		User user = userRepository.findByUserId(id);
		return user.isUserStatus();
	}

	@Override
	public boolean logOut(String token) throws UserException {
		Long id =  Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
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

	@Override
	public boolean updateUser(String userName, String password, String token) throws UserException {
		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		User isUserExist = userRepository.findByUserId(id);
		if (isUserExist != null) {
			User user = new User();
			BeanUtils.copyProperties(isUserExist, user);
			user.setUserName(userName);
			user.setPassword(password);
			user.setUpdateDateTime(DateUtility.today());
			userRepository.update(isUserExist, id);
			return true;
		}
		throw new UserException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE);
	}

	public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	@Override
	public void uploadFileTos3bucket(String fileName, File file) {
		amazonS3.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	@Override
	public String uploadFile(MultipartFile multipartFile, String token) throws IOException {
		String fileUrl = "";
		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		try {
			File file = convertMultiPartToFile(multipartFile);
			System.out.println("File " + file);
			String fileName = generateFileName(multipartFile);
			logger.info("File Name: " + fileName);
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			userRepository.saveImageUrl(fileUrl, id);
			file.delete();
		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl;

	}

	public boolean deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		logger.info("FileName: " + fileName);
		logger.info("bucketName: " + bucketName);
		amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		return true;
	}

	private User getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();
		User u = new User();
		for (SearchHit hit : searchHit) {
			u = (objectMapper.convertValue(hit.getSourceAsMap(), User.class));
		}
		return u;
	}

}
