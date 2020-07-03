package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;

public interface CartService {

	Cart addtocart(String token, long bookId);

	boolean removeBookFromCart(String token, long cartBookId);

	Cart displayItems(String token);

	CartBooks addQuantity(long cartBookId, String token);

	CartBooks removeQuantity(long cartBookId, String token);

}
