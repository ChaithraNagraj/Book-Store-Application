package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;

public interface CartService {

	Cart addtocart(String token, long bookId);
	boolean removeBookFromCart(String token, long bookId);
	List<Book> displayItems(String token);
	

}
