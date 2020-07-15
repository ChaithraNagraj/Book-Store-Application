package com.bridgelabz.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class BookStoreApplication {

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	private Role role;

	private final String[] ROLE_TYPE = new String[Constant.COUNT];
	{
		ROLE_TYPE[0] = "ADMIN";
		ROLE_TYPE[1] = "SELLER";
		ROLE_TYPE[2] = "BUYER";
	}
	
	

	@Bean
	public void setRole() {
		Role userRole = roleRepository.getRoleById(3);
		if(userRole==null) {
		for (int counter = 0; counter < Constant.COUNT; counter++) {
			System.out.println("setRole");
			role = new Role((long) 1, ROLE_TYPE[counter]);
			System.out.println(ROLE_TYPE[counter]);
			userRepository.saveRoles(role);
			System.out.println("main End");
		}}
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

}
