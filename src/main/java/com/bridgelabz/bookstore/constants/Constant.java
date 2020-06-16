package com.bridgelabz.bookstore.constants;

public class Constant {

	private Constant() {}



	public static final String LOGIN_TEMPLET = "C:\\Users\\SHUBHAM\\Desktop\\Java STS Workspace\\Book-Store-Application\\src\\main\\resources\\templates\\Login-template.html";
	public static final String FORGOT_PASSWORD_TEMPLET = "C:\\Users\\SHUBHAM\\Desktop\\Java STS Workspace\\Book-Store-Application\\src\\main\\resources\\templates\\Forgot-Password-template.html";
	public static final String REGISTRATION_TEMPLET = "C:\\Users\\SHUBHAM\\Desktop\\Java STS Workspace\\Book-Store-Application\\src\\main\\resources\\templates\\Registration-template.html";
	public static final String EMAIL_ID = "rameshaanji535@gmail.com";
	public static final String SECRET_KEY = "secret";
	public static final String EMAIL = "email";
	public static final String VERIFY_ADDRESS = "http://localhost:8080/user/verify?token=";
	public static final String ISSUER = "Bridgelabz";
	public static final String SUBJECT = "authentication";
	public static final long LOGIN_EXP = (long) 24 * 60 * 60 * 10000000;
	public static final int OK_RESPONSE_CODE = 200;
	public static final int CREATED_RESPONSE_CODE = 201;
	public static final int ALREADY_EXIST_EXCEPTION_STATUS = 208;
	public static final int NOTE_NOT_FOUND_EXCEPTION_STATUS = 300;
	public static final int BAD_REQUEST_RESPONSE_CODE = 400;
	public static final int USER_AUTHENTICATION_EXCEPTION_STATUS = 401;
	public static final int NOT_FOUND_RESPONSE_CODE = 404;
	public static final int BAD_GATEWAY_RESPONSE_CODE = 502;
	public static final String LOGIN_SUCCESSFULL_MESSAGE = "Login Successfull";
	public static final String PASSWORD_UPTATION_SUCCESSFULLY_MESSAGE = "Password Updated Successfully";
	public static final String VALID_INPUT_MESSAGE = "Give Valid Password";
	public static final String LOGIN_FAILED_MESSAGE = "Login Failed";
	public static final String USER_VERIFIED_SUCCESSFULLY_MEAASGE = "User Verified Successfully";
	public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found!";
	public static final String USER_ALREADY_VERIFIED_MESSAGE = "User Already Verified";
	public static final String USER_ALREADY_REGISTER_MESSAGE = "User Already register";
	public static final String REGISTRATION_SUCCESSFULL_MESSAGE = "Registration Sucessfull";
	public static final String REPLAY_MAILID = "no_replay@gmail.com";
	public static final String MESSAGEING_RESPONSE = "No-Reply";
	public static final String VERIFICATION = "Verification";
	public static final long REGISTER_EXP = (long) 3 * 60 * 60 * 1000;
//	public static final String RESET_PASSWORD = "http://localhost:8080/user/resetpassword/";
	public static final String PASSWORD_UPDATE_MESSAGE = "Password Updation Message";
	public static final String CHECK_MAIL_MESSAGE = "Please Check your Mail";
//	public static final String PATH = "E:\\Book-Store-Application\\src\\main\\resources\\templates\\Registration-template.html";
	public static final String USER_REGISTER_SUCESSFULLY = "User Registration Sucessfully Done.";
	public static final String PROFILE_IMAGE_UPLOADED_SUCCESSFULLY = "Profile Pic Uploded Successfully";
	public static final String PROFILE_IMAGE_DELETED_SUCCESSFULLY = "Profile Pic Deleted Successfully";
	public static final String LOGOUT_MEAASGE = "User LogOut Sucessfully";
	public static final String LOGOUT_FAILED_MEAASGE = "User LogOut Failed";
	public static final String USER_DETAILS_UPDATED_SUCCESSFULLY = "User Details Updated Successfully";
}