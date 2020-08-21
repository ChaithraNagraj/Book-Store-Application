package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.model.MyOrderList;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.dto.OrderDTO;

public interface OrderService {

	public Order checkOut(String token, OrderDTO orderDTO);

	List<MyOrderList> getOrders(String token);

	public void sendOrderSuccessMail(String token, Order order);

}
