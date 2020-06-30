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
import com.bridgelabz.bookstore.utils.JwtValidate;
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
		user.setMobileNumber("1234567890");

	}


//	@Test
//	final void testFindById() {
//		when(repo.findByUserId(1l)).thenReturn(user);
//		service.findById(1l);
//		 assertEquals("pallavi", user.getName());
//	}
//
//	@Test
//	final void testGetUser() {
//		List<User> userlist=new ArrayList<> ();
//		userlist.add(user);
//		when(repo.getUser()).thenReturn(userlist);
//		service.getUser();
//		assertEquals("pallavi", user.getName());
//	}
//	
//	@Test
//	void testExpectedExceptionGetUser() {
//		List<User> userlist=new ArrayList<> ();
//		when(repo.getUser()).thenReturn(userlist);
//		if(userlist.isEmpty())
//		{
//	  Assertions.assertThrows(UserNotFoundException.class, () -> {
//		  service.getUser();
//	  });
//		}
//	}
	
//	@Test
//  void deleteUserByIdTest() {
//	  long  doseId=42;
//
//      // perform the call
//      service.deleteUserById(doseId);
//
//      // verify the mocks
//      verify(repo,times(1)).delete((long) ((int)doseId));
//  }
	@Test
	  void VerifyTest() throws UserException {
		Role role=new Role();
		role.setRole("seller");
		role.setRoleId(2l);
		List<User> userlist=new ArrayList<> ();
		userlist.add(user);
		role.setUser(userlist);
//		long id=1;
//		int rid=2;
		
		//when(roleRepository.getRoleById((int) rid)).thenReturn(role);
		// role=roleRepository.getRoleById((int) rid);
		// when(repo.findByUserId(1l)).thenReturn(user);
		//User idAvailable=repo.findByUserId(1l);
		//System.out.println("rolename"+idAvailable.getName());
		
		//idAvailable.setVerify(false);
		//System.out.println("idAvailable"+idAvailable.isVerify());
		//perform call
		
	//when(service.verify("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw")).thenReturn(true);
		service.verify("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw");
		Long Id = Long.valueOf((Integer) JwtValidate.decodeJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw").get("userId"));
		long roleId = Long.valueOf((Integer) JwtValidate.decodeJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInJvbGVJZCI6MiwiaWF0IjoxNTkzNDU3ODQwLCJzdWIiOiJhdXRoZW50aWNhdGlvbiIsImlzcyI6IkJyaWRnZWxhYnoiLCJleHAiOjI0NTc0NTc4NDB9.HMHnoMRXV-cMnoZch2lDxofHOn9jVrgO9So9-12vVQw").get("roleId"));
		when(roleRepository.getRoleById((int) roleId)).thenReturn(role);
		User idAvailable=repo.findByUserId(Id);
		System.out.println("rolename"+idAvailable.getName());
		idAvailable.setVerify(false);
		
		verify(repo, times(1)).verify(idAvailable.getId());
		when(repo.findByUserId(1l)).thenReturn(user);
	
		
	}
  

	
	@Test
	final void testgetUser(){
		
		User user=new User();
		user.setEmail("email@email.com");
		user.setId(1l);
	
		user.setMobileNumber("1234567890");
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
			when(repo.getUser()).thenReturn(ListOfUsr);
			assertEquals("email@email.com",ListOfUsr.get(0).getEmail());
		
	}


}
