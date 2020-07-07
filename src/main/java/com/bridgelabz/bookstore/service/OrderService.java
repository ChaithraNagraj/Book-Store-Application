package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.MyOrder;
import com.bridgelabz.bookstore.model.MyOrderItems;
import com.bridgelabz.bookstore.model.MyOrderList;

public interface OrderService {

	public void addOrder(String token);

	public List<MyOrderList> getOrders(String token);

}
