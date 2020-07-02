package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;

public interface CartService {

	Cart addtocart(String token, long bookId);

	boolean removeBookFromCart(String token, long cartBookId);

	List<Book> displayItems(String token);

	CartBooks addQuantity(long cartBookId, String token);

	CartBooks removeQuantity(long cartBookId, String token);

}
