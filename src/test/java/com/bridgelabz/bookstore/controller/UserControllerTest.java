package com.bridgelabz.bookstore.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.service.UserService;

class UserControllerTest {
	
	@InjectMocks
	UserController Usercontroller;
	
	
	@Mock
	UserService service;
	User user=new User();
	

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		user=new User();
		user.setEmail("email@email.com");
		user.setId(1l);
	    user.setImageUrl("imageUrl");
		user.setMobileNumber(1234567890l);
		user.setName("pallavi");
	    user.setUserName("Wxcvbn");
	    user.setUserStatus(false);
	    user.setVerify(false);
	}

	@Test
	final void testGetUserById() {
		//ResponseEntity r=new ResponseEntity(HttpStatus.ACCEPTED);
		when(service.findById(1l)).thenReturn(user);
		    // Usercontroller.getUserById(1l);
//		assertNotNull(user);
//		 Mockito
//         .when(Usercontroller.getUserById(1l))
//         .thenReturn(new ResponseEntity(user, HttpStatus.OK));
		   Usercontroller.getUserById(1l);
		 assertEquals("pallavi", user.getName());
	
		
	}

}
