package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.RedisCache;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BookRepo bookRepository;

	@Autowired
	UserRepo userRepository;
	
	@Autowired
	private RedisCache<Object> redis;

	@Autowired
	private MailTempletService mailTempletService;

	@Autowired
	private Environment environment;

	@Override
	public List<User> getSellers(String token) {
		
		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if(id==1) {
			userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
					Constant.NOT_FOUND_RESPONSE_CODE));
			}
			else {
				throw new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
						Constant.NOT_FOUND_RESPONSE_CODE);
			}
		List<User> sellers = roleRepository.getRoleByName("seller").getUser();
		
		if(sellers.isEmpty()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		int size=sellers.size();
		int ctr=0;
		for(int i=0;i<size;i++) {
			
			List<Book> book=sellers.get(i-ctr).getSellerBooks().stream().filter(b->b.isApproved()==false).collect(Collectors.toList());
			if(book.isEmpty()) {
				sellers.remove(i-ctr);
				ctr++;
			}
			book.clear();
			
		}
		if(sellers.isEmpty()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		return sellers;
	}

	@Override
	public List<Book> getBooksForVerification(long sellerId, String token) {
		
		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if(id==1) {
		userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
				Constant.NOT_FOUND_RESPONSE_CODE));
		}
		else {
			throw new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}
		User seller = userRepository.findByUserId(sellerId);		
		List<Book> books = seller.getSellerBooks();
		if(books.isEmpty()) {
			throw new UserNotFoundException(Constant.BOOK_NOT_FOUND,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}		
		List<Book> book = books.stream().filter(b->b.isApproved()==false).collect(Collectors.toList());
		if(book.isEmpty()) {
			throw new UserNotFoundException(Constant.BOOK_NOT_FOUND,
					Constant.NOT_FOUND_RESPONSE_CODE);
		}	
		return book;
	}

	

	@Override
	public void bookVerification(Long bookId, Long sellerId, boolean verify, String token) throws BookException {
		
		Long id = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		if(id==1) {
			userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
					Constant.NOT_FOUND_RESPONSE_CODE));
			}
			else {
				throw new UserNotFoundException(Constant.ADMIN_CREDENTIALS_MISMATCH,
						Constant.NOT_FOUND_RESPONSE_CODE);
			}
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookException(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));

		User seller = userRepository.findByUserId(sellerId);
		Role role = roleRepository.getRoleByName("SELLER");
		if (verify) {
			book.setApproved(true);
			bookRepository.save(book);
			registerMail(seller, role, environment.getProperty("book-approval-template-path"));
		} else {
			book.setApproved(false);
			book.setNoOfRejections(book.getNoOfRejections() + 1);
			if (book.getNoOfRejections() > 2) {
				bookRepository.deleteBook(book);
				registerMail(seller, role, environment.getProperty("book-deletion-template-path"));
			} else {

				bookRepository.save(book);
				registerMail(seller, role, environment.getProperty("book-rejection-template-path"));
			}
		}

	}

	public void registerMail(User user, Role role, String templet) {
		String token = TokenUtility.verifyResponse(user.getId(), role.getRoleId());
		sendMail(user, token, templet);
	}

	public void sendMail(User user, String token, String templet) {
		try {
			mailTempletService.getTemplate(user, token, templet);
		} catch (IOException e) {

		}
	}

}
