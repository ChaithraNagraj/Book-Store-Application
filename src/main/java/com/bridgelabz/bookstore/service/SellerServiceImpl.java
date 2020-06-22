package com.bridgelabz.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookAlreadyExistsException;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private UserRepo userRepository;

	private User authentication(String token) {
		Long userId = JwtValidate.decodeJWT(token);
		Long roleId = 2L;
		return Optional.ofNullable(userRepository.findByUserIdAndRoleId(userId, roleId))
				.orElseThrow(() -> new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE));

	}

	@Override
	public Book addBook(BookDto newBook, String token) {
		User seller = authentication(token);
		seller.getSellerBooks().stream().filter(book -> book.getBookName().equals(newBook.getBookName())).findAny()
				.ifPresent(action -> {
					throw new BookAlreadyExistsException("Book Already Exists In Your Inventory");
				});
		Book book = new Book();
		BeanUtils.copyProperties(newBook, book);
		book.setCreatedDateAndTime(DateUtility.today());
		book.setNoOfRejections(0);
		seller.getSellerBooks().add(book);
		userRepository.addUser(seller);
		return book;
	}

	@Override
	public Book updateBook(BookDto updatedBookInfo, long bookId, String token) {
		User seller = authentication(token);
		Book bookToBeUpdated = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId))
				.findAny().orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		BeanUtils.copyProperties(updatedBookInfo, bookToBeUpdated);
		bookToBeUpdated.setApproved(false);
		bookToBeUpdated.setLastUpdatedDateAndTime(DateUtility.today());
		userRepository.addUser(seller);
		return bookToBeUpdated;
	}

	@Override
	public List<Book> getAllBooks(String token) {
		User seller = authentication(token);
		return seller.getSellerBooks();
	}

	@Override
	public boolean removeBook(long bookId, String token) {
		User seller = authentication(token);
		Book bookTobeDeleted = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId))
				.findAny().orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		seller.getSellerBooks().remove(bookTobeDeleted);
		userRepository.addUser(seller);
		return true;
	}

	@Override
	public Book addQuantity(long bookId, String token, int quantity) {
		User seller = authentication(token);
		Book bookToAddQuantity = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId)).findAny()
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		quantity += bookToAddQuantity.getQuantity();
		bookToAddQuantity.setQuantity(quantity);
		bookToAddQuantity.setLastUpdatedDateAndTime(DateUtility.today());
		userRepository.addUser(seller);
		return bookToAddQuantity;
	}

}
