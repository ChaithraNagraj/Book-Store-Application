package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.CartRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
@Component
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
	public boolean addtocart(String token, long bookId) {

		Long userId = (Long) JwtValidate.decodeJWT(token).get("userId");
		Book bookk = bookRepo.findByBookId(bookId);
		List<Book> cartBooks = new ArrayList<>();
		cartBooks.add(bookk);
		User user = userRepo.findByUserId(userId);
		long userid = user.getId();
		Cart cart = new Cart();
		cart.setBooks(cartBooks);
		cart.setUser(user);
		boolean a = cartRepo.saveToCart(cart);
		if (a == true) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeBookFromCart(String token, long bookId) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		List<Book> booksInCart = buyer.getUserCart().getBooks();
		Book bookToBeRemoved=booksInCart.stream().filter(books -> books.getBookId() == bookId).findAny().orElseThrow(() -> {
			throw new BookNotFoundException(Constant.BOOK_NOT_FOUND);
		});
		booksInCart.remove(bookToBeRemoved);
		buyer.getUserCart().setBooks(booksInCart);
		return cartRepo.saveToCart(buyer.getUserCart());
	}

}
