package com.bridgelabz.bookstore.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.service.UserService;

class UserControllerTest {

	@InjectMocks
	UserController Usercontroller;

	@Mock
	UserService service;
	User user = new User();

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("email@email.com");
		user.setId(1l);
		user.setImageUrl("imageUrl");
		user.setMobileNumber("1234567890");
		user.setName("pallavi");
		user.setUserName("Wxcvbn");
		user.setUserStatus(false);
		user.setVerify(false);
	}

//	@Test
//	final void testGetUserById() {
//		when(service.findById(1l)).thenReturn(user);
//		   Usercontroller.getUserById(1l);
//		 assertEquals("pallavi", user.getName());
//	}

}
