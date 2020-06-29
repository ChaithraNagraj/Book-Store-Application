package com.bridgelabz.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.RoleRepositoryImp;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.MailTempletService;

class UserServiceImpTest {
	
	@InjectMocks
	UserServiceImp service;
	
	
	@Spy
	UserRepo repo;
	User user=new User();
	
	@Mock
	RoleRepositoryImp roleRepository;

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
	final void testFindById() {
		when(repo.findByUserId(1l)).thenReturn(user);
		service.findById(1l);
		 assertEquals("pallavi", user.getName());
	}

	@Test
	final void testGetUser() {
		List<User> userlist=new ArrayList<> ();
		userlist.add(user);
		when(repo.getUser()).thenReturn(userlist);
		service.getUser();
		assertEquals("pallavi", user.getName());
	}
	
	@Test
	void testExpectedExceptionGetUser() {
		List<User> userlist=new ArrayList<> ();
		when(repo.getUser()).thenReturn(userlist);
		if(userlist.isEmpty())
		{
	  Assertions.assertThrows(UserNotFoundException.class, () -> {
		  service.getUser();
	  });
		}
	}
	
	@Test
  void deleteUserByIdTest() {
	  long  doseId=42;

      // perform the call
      service.deleteUserById(doseId);

      // verify the mocks
      verify(repo,times(1)).delete((long) ((int)doseId));
  }
	@Test
	  void VerifyTest() throws UserException {
		Role role=new Role();
		role.setRole("admin");
		role.setRoleId(1l);
		List<User> userlist=new ArrayList<> ();
		userlist.add(user);
		role.setUser(userlist);
		long id=42;
		int rid=1;
		//verify(roleRepository,times(1)).getRoleById(rid);
		when(roleRepository.getRoleById((int) rid)).thenReturn(role);
		 role=roleRepository.getRoleById((int) rid);
		// System.out.println("rolename"+ role.getRole());
		 when(repo.findByUserId(1l)).thenReturn(user);
		User idAvailable=repo.findByUserId(1l);
		System.out.println("rolename"+idAvailable.getId());
		
		idAvailable.setVerify(true);
		//perform call
		//verify(service,times(1)).verify("xyz");
	//when(service.verify("xyz")).thenReturn(true);
		service.verify("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzMDIzMDU2LCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjE1OTMwMzM4NTZ9.vpEg6e11UAwYtNP2iChGoMF_zsMCN6mCg3FDkxNeztw");
		verify(repo, times(1)).verify(idAvailable.getId());
		//verify(service,times(1)).verify("xyz");
		
	}
  
}
