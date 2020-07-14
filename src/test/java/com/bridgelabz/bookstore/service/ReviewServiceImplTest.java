package com.bridgelabz.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bridgelabz.bookstore.constants.AdminConstants;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.UserRepo;

class ReviewServiceImplTest {
	@InjectMocks
	ReviewServiceImpl service;
	
	@Mock
	UserRepo userRepository;
	
	@Mock
	BookRepo bookRepository;
	
	User user = new User();
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("email@email.com");
		user.setName("pallavi");
		user.setId(1l);
		user.setImageUrl("imageUrl");
		user.setMobileNumber("1234567890");
		user.setSellerBooks(book());
		user.setRoleList(role());
	}

	public List<Book> book(){
		Book books=new Book();
		books.setBookId(1l);
		books.setApprovalSent(false);
		books.setBookName("my love");
		Book book2= new Book();
		book2.setBookId(2l);
		book2.setApprovalSent(true);
		book2.setBookName("high priority");
		List<Book> booklist=new ArrayList<> ();
		booklist.add(books);
		booklist.add(book2);
		return booklist;		
	}
	public List<Role> role(){
		List<Role> rolelist=new ArrayList<> ();
		Role roles=new Role();
		roles.setRole("seller");
		roles.setRoleId(2l);
		rolelist.add(roles);
		return rolelist;
	}
	@Test
	final void testAddRating() throws Throwable {
		Optional user1 = Optional.ofNullable(user);
			user1.orElseThrow(() -> new UserNotFoundException(AdminConstants.ADMIN_CREDENTIALS_MISMATCH,
					AdminConstants.NOT_FOUND_RESPONSE_CODE));
		when(userRepository.getUserById(1l)).thenReturn(user1);	
		
		
		
	}

//	@Test
//	final void testGetReview() {
//		fail("Not yet implemented");
//	}

}
