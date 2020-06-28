package com.bridgelabz.bookstore.service;

public interface CartService {

	boolean addtocart(String token, long bookId);
	boolean removeBookFromCart(String token, long bookId);
	

}
