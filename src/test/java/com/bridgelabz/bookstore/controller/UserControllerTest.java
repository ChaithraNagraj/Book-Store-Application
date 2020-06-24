package com.bridgelabz.bookstore.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.http.client.methods.RequestBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.bookstore.config.AmazonClient;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService service;
	
	@MockBean
	AmazonClient awsService;
	
	@MockBean
	UserRepo user;
	
	@Before
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	

	


	@Test
	final void testRegister() {
		//String url ="http://localhost:8080/"+ "/users/register";
		        try {
					mockMvc.perform(post("/users/register")
					.header("headers", "Accept=application/json")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(new RegistrationDTO("Pallavi","P_1TRng","pallavikumari2207@gmail.com","S_1tring","2",9122449097l)))
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
				} catch (Exception e) {
					System.out.println("exception" + e);
					e.printStackTrace();
				}
	}
	RegistrationDTO reg=new RegistrationDTO();
	LoginDTO lg=new LoginDTO();

	@Test
	public void loginTest() throws Exception{
		
//		
//	    RequestBuilder requestBuilder = post("/login")
//	            .param("username", lg.setloginId("1"));
//	            .param("password", lg.setPassword("P_assword");
//	    mockMvc.perform(requestBuilder)
//	            .andDo(print())
//	            .andExpect(status().isOk())
//					
	}

	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
