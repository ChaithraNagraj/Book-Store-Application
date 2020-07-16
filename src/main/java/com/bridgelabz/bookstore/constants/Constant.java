package com.bridgelabz.bookstore.constants;

import org.springframework.http.HttpStatus;

public class Constant {

	private Constant() {
	}

	public static final String KEY = "User";
	public static final String ROLE_AS_ADMIN = "ADMIN";
	public static final String ROLE_AS_SELLER = "SELLER";
	public static final String ROLE_AS_BUYER = "BUYER";
	public static final String EMAIL_ID = "rameshaanji535@gmail.com";
	public static final String SECRET_KEY = "secret";
	public static final String EMAIL = "email";
// 	public static final String VERIFY_ADDRESS = "http://localhost:8080/users/verify?token=";
	// public static final String VERIFY_ADDRESS =
	// "http://localhost:8080/users/verify?token=";
	public static final String VERIFY_ADDRESS = "http://localhost:4200/verificationSuccess/";
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
	public static final int ACCEPT_RESPONSE_CODE = HttpStatus.ACCEPTED.value();
	public static final int EXPECTATION_FAILED_RESPONSE_CODE = HttpStatus.EXPECTATION_FAILED.value();
	public static final String PROFILE_IMAGE_DELETED_SUCCESSFULLY = "PROFILE_IMAGE_DELETED_SUCCESSFULLY";
	public static final String PROFILE_IMAGE_UPLOADED_SUCCESSFULLY = "PROFILE_IMAGE_UPLOADED_SUCCESSFULLY";
	public static final String INDEX = "bookentity";
	public static final String TYPE = "doc";
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
	public static final String PASSWORD_UPDATE_MESSAGE = "Password Updation Message";
	public static final String CHECK_MAIL_MESSAGE = "Please Check your Mail";
	public static final String USER_REGISTER_SUCESSFULLY = "User Registration Sucessfully Done.";
	public static final String LOGOUT_MEAASGE = "User LogOut Sucessfully";
	public static final String LOGOUT_FAILED_MEAASGE = "User LogOut Failed";
	public static final String USER_DETAILS_UPDATED_SUCCESSFULLY = "User Details Updated Successfully";
	public static final String BOOK_NOT_FOUND = "Book Not found";
	public static final String USER_VERIFIED_FAILD_MEAASGE = "User Verification Failed";
	public static final String USER_REGISTER_FAILED = "User Registration Failed";
	public static final String DISPLAYING_BOOKS = "Displaying Books";
	public static final String BOOK_FOUND = "book found";
	public static final String BOOK_ADDITION_SUCCESSFULL_MESSAGE = "Book Added Successfully";
	public static final String BOOK_ADDITION_FAILED_MESSAGE = "Book Adding Failed";
	public static final String BOOK_UPDATION_SUCCESSFULL_MESSAGE = "Book Updation Successfull";
	public static final String BOOK_UPDATION_FAILED_MESSAGE = "Book Updation Failed";
	public static final String BOOK_DELETION_SUCCESSFULL_MESSAGE = "Book Removed from your Inventory";
	public static final String BOOK_QUANTITY_ADDITION_SUCCESSFULL = "Quantity Addition to Book successfull";
	public static final String PROFILE_IMAGE_DELETED_FAILED = "Image Deleted Failed";
	public static final String PROFILE_IMAGE_UPLOADED_FAILED = "Image Uploaded Failed";
	public static final String USER_DETAILS_UPDATED_FAILED = "User Details Updated Failed";
	public static final String RESET_PASSWORD = "http://localhost:4200/resetpassword/";

	public static final String ADMIN_CREDENTIALS_MISMATCH = "Admin Credentials Mismatch";
	public static final String USER_FOUND = "User Found";
	public static final String BOOK_VERIFIED_SUCCESSFULLY_MEAASGE = "Book Verified Successfully";

	public static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "You are not Authorized";
	public static final String APPROVAL_STATUS_WAITING = "waiting";
	public static final String APPROVAL_STATUS_APPROVED = "approved";
	public static final String APPROVAL_STATUS_REJECTED = "rejected";
	public static final String APPROVAL_STATUS_CREATED = "created";
	public static final String USER_ID = "userId";
	public static final String ROLE_ID = "roleId";
	public static final String BOOK_ADD_TO_CART = "book is succesfully added to the cart";
	public static final String BOOK_ADD_TO_CART_FAILED = "book couldnot/failed to add to the cart";
	public static final String BOOK_REMOVED_FROM_CART = "book is sucessfully removed from cart";
	public static final String BOOK_REMOVAL_FROM_CART_FAILED = "Failed to remove book from cart";
	public static final String BOOKS_DISPLAYING_MESSAGE = "Displaying Books in cart";
	public static final String BOOKS_DISPLAYING_FAILED_MESSAGE = "Displaying Books in cart failed";
	public static final String ITEM_ALREADY_EXISTS_EXCEPTION_MESSAGE = "Book Already added to your cart";
	public static final String QUANTITY_INCREASED_SUCCESS_MESSAGE = "Quantity increased successfully";
	public static final String QUANTITY_INCREASED_FAILED_MESSAGE = "Quantity increased Failed";
	public static final String QUANTITY_DECREASED_SUCCESS_MESSAGE = "Quantity decreased successfully";
	public static final String QUANTITY_DECREASED_FAILED_MESSAGE = "Quantity decreased Failed";
	public static final String BOOK_NOT_FOUND_IN_CART_MESSAGE = "Book does not exists In your Cart";
	public static final String BOOK_OUT_OF_STOCK_MESSAGE = "Book Out Of stock";
	public static final String CART_ITEMS_LIMIT_EXCEEDED_MESSAGE = "Cart Books exceeded limit of 5";
	public static final String CART_ITEM_LOW_LIMIT_MESSAGE = "Books In cart can't be less than one";
	public static final String CART_EMPTY_MESSAGE = "No Books In cart To remove quantity";

	public static final String BOOK_ADD_TO_WISHLIST = "book is succesfully added to the wishlist";
	public static final String BOOK_ADD_TO_WISHLIST_FAILED = "failed to add to the wishlist";

	public static final String BOOK_REMOVED_FROM_WISHLIST = "book is sucessfully removed from wishlist";
	public static final String BOOK_REMOVAL_FROM_WISHLIST_FAILED = "failed to remove book from wishlist";

	public static final String BOOKS_DISPLAY_MESSAGE = "Displaying items in wishlist";
	public static final String BOOKS_DISPLAY_FAILED_MESSAGE = "Displaying items in wishlist failed";

	public static final String ADDRESS_DETAILS_ADDED = "address details added succefully";
	public static final String ADDRESS_DETAILS_FAIL = " address details failed to add ";
	public static final String ADDRESS_DETAILS_FOUND = "address details found for required type succefully";
	public static final String ADDRESS_DETAILS_NOT_fOUND = " not found address details for required type ";

	public static final String ORDER_PLACED_SUCCESSFULLY = "Order placed Successfully";

	public static final String ORDER_PLACED_FAILED = "Order placed Failed";

	public static final String PLACE_ORDER_SUCCESSFUL_MESSAGE = "Order placed";
	public static final String PLACE_ORDER_FAILED_MESSAGE = "Order placing Failed";

	public static final String LIST_OF_ORDERS = "List of orders";
	public static final String ORDER_EMPTY = "Order Empty";

	public static final String USER_NOT_EXIST_PLEASE_REGISTER = "We cannot find an account with that Login credentials";

	public static final String PLEASE_GIVE_CORRECT_PASSWORD = "Please Check Your Password";

	public static final String YOUR_EMAIL_NOT_VERIFIED = "Your Email Not Verified";

	public static final String USER_NOT_FOUND_WITH_ROLE = "User Not Exist With Corresponding Role";

	public static final String QUANTITY_UPDATION_SUCCESS_MESSAGE = "Quantity Updation success";

	public static final String QUANTITY_UPDATION_FAILED_MESSAGE = "Quantity Updation Failed";

	public static final String CART_SIZE_FETCHED_SUCCESSFULLY = "Cart size fetched successfully";

	public static final String CART_SIZE_FETCHING_FAILED = "Cart size fetched failed";

	public static final int COUNT = 3;
	

}