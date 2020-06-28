package com.bridgelabz.bookstore.repo;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.Cart;
@Repository
@Transactional
public interface CartRepo {

	boolean saveToCart(Cart cart);
	

}
