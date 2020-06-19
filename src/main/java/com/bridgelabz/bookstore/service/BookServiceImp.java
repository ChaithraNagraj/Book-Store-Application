package com.bridgelabz.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

@Service
@Component
public class BookServiceImp implements BookService {

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepo userRepository;

	@Override
	public List<Book> findBookByAuthorName(String authorName) {
		return bookRepository.findBookByAuthorName(authorName);
	}

	@Override
	public List<Book> findBookByTitle(String title) {
		return bookRepository.findBookByTitle(title);
	}

	@Override
	public List<Book> findAllBook() {
		return bookRepository.findAllBooks();
	}

	@Override
	public void addBook(BookDto details, Long userId) {

		Book bookEntity = new Book();
		BeanUtils.copyProperties(details, bookEntity);
		bookEntity.setCreatedDateAndTime(LocalDateTime.now());
		bookEntity.setLastUpdatedDateAndTime(LocalDateTime.now());
		bookEntity.setVerifiedDateAndTime(LocalDateTime.now());
		bookEntity.setApproved(false);
		bookEntity.setNoOfRejections(0);	
		User user = userRepository.findByUserId(userId);
		user.getSellerBooks().add(bookEntity);
		userRepository.addUser(user);
	}
}
