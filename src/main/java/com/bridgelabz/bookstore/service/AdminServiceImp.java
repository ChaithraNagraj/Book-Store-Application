package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookException;
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

		return roleRepository.getRoleById(2).getUser();
	}

	@Override
	public List<User> getSellers() {

		return roleRepository.getRoleById(3).getUser();
	}

	@Override
	public List<Book> getAllBooks() {
		
		return bookRepository.findAllBooks();
	}

	@Override
	public List<Book> getBooksForVerification() {
		
		return bookRepository.getBooksForVerification();
	}

	@Override
	public void bookVerification(Long bookId, Long sellerId, String verify) throws BookException {
		Book book = bookRepository.getBookById(bookId).orElseThrow(()-> new BookException(Constant.BOOK_NOT_FOUND , Constant.NOT_FOUND_RESPONSE_CODE));
		User seller=userRepository.findByUserId(sellerId);
		if(verify.equals("yes")) {
			book.setIsapproved(true);
			bookRepository.save(book);
			registerMail(seller, environment.getProperty("book-approval-template-path"));
		}
		else {
			book.setIsapproved(false);
			book.setNoOfApprovals(book.getNoOfApprovals()+1);
			if(book.getNoOfApprovals()>2) {
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
