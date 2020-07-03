package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.Order;

public interface OrderService {
	public List<Order> myOrder(String token);

	public Order checkOut(Long id, int quantity, String token);

}
