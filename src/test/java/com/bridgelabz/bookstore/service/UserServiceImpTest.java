package com.bridgelabz.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.DateUtility;

class UserServiceImpTest {
	
	@InjectMocks
	UserServiceImp userService;
	
	@Mock
	UserRepo UserRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	final void testFindById() {
		User user=new User();
		user.setEmail("email@email.com");
		user.setId(1l);
	
		user.setMobileNumber(1234567890l);
		user.setName("pallavi");
	    user.setUserName("Wxcvbn");
	    user.setUserStatus(false);
	    user.setVerify(false);
		when(UserRepository.findByUserId(1l)).thenReturn(user);
		userService.findById(1l);
		assertNotNull(user);
		assertEquals("pallavi", user.getName());
	
	}
	
	@Test
	final void testgetUser(){
		
		User user=new User();
		user.setEmail("email@email.com");
		user.setId(1l);
	
		user.setMobileNumber(1234567890l);
		user.setName("pallavi");
	    user.setUserName("Wxcvbn");
	    user.setUserStatus(false);
	    user.setVerify(false);
	    user.setRegistrationDateTime(null);
	    user.setSellerBooks(null);
	    user.setRoleList(null);
	    user.setVerify(false);
	    
			List<User> ListOfUsr=new ArrayList<>();
			ListOfUsr.add(user);
			when(UserRepository.getUser()).thenReturn(ListOfUsr);
			assertEquals("email@email.com",ListOfUsr.get(0).getEmail());
		
	}

}
