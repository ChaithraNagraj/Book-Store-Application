package com.bridgelabz.bookstore.service;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.TokenUtility;
@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BookRepo bookRepository;
	
	@Autowired
	UserRepo userRepository;
	
	@Autowired
	private MailTempletService mailTempletService;
	
	
	@Autowired
	private Environment environment;

	@Override
	public List<User> getBuyers() {

		List<User> buyers = roleRepository.getRoleByName("buyer").getUser();
		if(buyers.isEmpty()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		return buyers;
	}

	@Override
	public List<User> getSellers() {

		List<User> sellers = roleRepository.getRoleByName("seller").getUser();
		if(sellers.isEmpty()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		return sellers;
	}

	@Override
	public List<Book> getAllBooks() {
		
		List<Book> books = bookRepository.findAllBooks();
		if(books.isEmpty()) {
			throw new UserNotFoundException(Constant.BOOK_NOT_FOUND,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}		
		List<Book> book = books.stream().filter(b->b.isApproved()).collect(Collectors.toList());
		if(book.isEmpty()) {
			throw new UserNotFoundException(Constant.BOOK_NOT_FOUND,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}	
		return book;
	}

	@Override
	public List<Book> getBooksForVerification() {
		
		List<Book> books = bookRepository.getBooksForVerification();
		if(books.isEmpty()) {
			throw new UserNotFoundException(Constant.BOOK_NOT_FOUND,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		return bookRepository.getBooksForVerification();
	}

	@Override
	public void bookVerification(Long bookId, Long sellerId, String verify) throws BookException {
		Book book = bookRepository.getBookById(bookId).orElseThrow(()-> new BookException(Constant.BOOK_NOT_FOUND , Constant.NOT_FOUND_RESPONSE_CODE));
		
		User seller=userRepository.findByUserId(sellerId);
		if(verify.equals("yes")) {
			book.setApproved(true);
			bookRepository.save(book);
			registerMail(seller, environment.getProperty("book-approval-template-path"));
		}
		else {
			book.setApproved(false);
			book.setNoOfRejections(book.getNoOfRejections()+1);
			if(book.getNoOfRejections()>2) {
				bookRepository.deleteBook(book);
				registerMail(seller, environment.getProperty("book-deletion-template-path"));
			}
			else {
				
				bookRepository.save(book);
				registerMail(seller, environment.getProperty("book-rejection-template-path"));
			}
		}
		
	}

	
	public void registerMail(User user, String templet) {
		String token = TokenUtility.verifyResponse(user.getId());
		sendMail(user,token, templet);
	}

	public void sendMail(User user, String token, String templet) {
		try {
			mailTempletService.getTemplate(user, token, templet);
		} catch (IOException e) {
			
		}
	}


}
