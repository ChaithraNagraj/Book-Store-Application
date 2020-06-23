package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.User;

public interface AdminService {
	

	public List<User> getSellers();

	public List<Book> getAllBooks();
	public List<Book> getBooksForVerification();
	public void bookVerification(Long bookId, Long sellerId, String verify) throws BookException;

	List<User> getBuyers();

}
