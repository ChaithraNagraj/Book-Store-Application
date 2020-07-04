package com.bridgelabz.bookstore.repo;

import java.util.List;

import com.bridgelabz.bookstore.model.Order;

public interface OrderRepo {
	
	public void addOrder(Order order);

	public List<Order> findMyOrder(Long id);


}

