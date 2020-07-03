package com.bridgelabz.bookstore.service;
public interface OrderService {

	public boolean checkOut(Long id, int quantity, String token);

}
