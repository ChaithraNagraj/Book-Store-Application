package com.bridgelabz.bookstore.usermoduletest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.service.UserServiceImp;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	@Autowired
	private UserServiceImp userServiceImpl;
	@Mock
	@Autowired
	private UserRepo userRepo;
	@Mock
	@Autowired
	private WebSecurityConfig encrypt;

	@Test
	public void sucessful_registration_user() throws UserException {
		RegistrationDTO newUser = new RegistrationDTO();
		newUser.setEmail("rameshaanji97@gmail.com");
		newUser.setMobileNumber(8428443096L);
		newUser.setName("Aanji Ram");
		newUser.setPassword("Aanji20!");
		newUser.setUserName("Aanji20@");
//		Mockito.when(encrypt.bCryptPasswordEncoder()).thenReturn(bCryptPasswordEncoder);
//		Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
		Boolean registrationResponse = userServiceImpl.registerUser(newUser);
		Assert.assertTrue(registrationResponse);
	}
}
