package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.MyOrder;
import com.bridgelabz.bookstore.model.MyOrderItems;

public interface OrderService {

	public void addOrder(String token);

	public List<MyOrder> getOrders(String token);

}
