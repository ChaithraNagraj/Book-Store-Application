package com.bridgelabz.bookstore.repo;

import java.util.Optional;

import com.bridgelabz.bookstore.model.Cart;

public interface CartRepo {

	boolean saveToCart(Cart cart);

	Optional<Cart> findByUserId(Long id);

}
