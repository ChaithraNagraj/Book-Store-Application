package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.CartRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.JwtValidate;

@Service
@Component
public class CartServiceImpl implements CartService {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	BookRepo bookRepo;
	@Autowired
	CartRepo cartRepo;

	@Override
	public boolean addtocart(String token, long bookId) {
		
		Long userId= (Long) JwtValidate.decodeJWT(token).get("userId");
		Book bookk= bookRepo.findByBookId(bookId);
		 List<Book> cartBooks = new ArrayList<>();
		 cartBooks.add(bookk);
		User user=userRepo.findByUserId(userId);
		long userid=user.getId();
		Cart cart=new Cart();
		cart.setBookId(cartBooks);
		cart.setUserId(user);
		boolean a=cartRepo.saveToCart(cart);
		if(a==true) {
			return true;
		}
		return false;		
	}

}
