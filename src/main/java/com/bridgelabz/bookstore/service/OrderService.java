package com.bridgelabz.bookstore.service;




public interface OrderService {

	public boolean makeOrder(Long id,int quantity,String token);
}