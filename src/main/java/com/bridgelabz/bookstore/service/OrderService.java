package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.model.Order;

public interface OrderService {

	public Order checkOut(Long id, int quantity, String token);

}
