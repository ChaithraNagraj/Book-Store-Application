package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.model.dto.UpdateBookDto;

public interface SellerService {

	Book addBook(BookDto newBook, String token);

	Book updateBook(UpdateBookDto updatedBookInfo, long bookId, String token);

	List<Book> getAllBooks(String token);

	boolean removeBook(long bookId, String token);

	Book addQuantity(long bookId, String token, int quantity);

	List<Book> searchBook(String token, String input) throws IOException;

	boolean sentForApproval(long bookId, String token);

}
