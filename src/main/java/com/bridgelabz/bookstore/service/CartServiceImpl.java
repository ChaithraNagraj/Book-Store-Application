package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.CartRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private TokenUtility tokenUtility;

	@Override
	@Transactional
	public boolean addtocart(String token, long bookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Book book = bookRepo.getBookById(bookId).orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		Cart cart = Optional.ofNullable(buyer.getUserCart()).orElse(new Cart());
		List<Book> booksInCart = Optional.ofNullable(cart.getBooks()).orElse(new ArrayList<>());
		cart.setUser(buyer);
		booksInCart.add(book);
		cart.setBooks(booksInCart);
		buyer.setUserCart(cart);
		return cartRepo.saveToCart(cart);
	}

}
