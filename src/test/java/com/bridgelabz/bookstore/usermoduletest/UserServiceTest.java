package com.bridgelabz.bookstore.usermoduletest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.bridgelabz.bookstore.config.WebSecurityConfig;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.repo.UserRepo;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserServiceImp.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpTest userServiceImpl;

	@Mock
	private UserRepo userRepo;
	
	@Mock
	private WebSecurityConfig encrypt;
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void sucessful_registration_user() throws UserException {
		RegistrationDTO registrationDTO = new RegistrationDTO("Ramesh Aanji", "Aanji20@", "rameshaanji97@gmail.com", "Aanji20@", "2", "8422442099");
		boolean response = userServiceImpl.registerUser(registrationDTO);
		Assert.assertTrue(response);
	}
}
