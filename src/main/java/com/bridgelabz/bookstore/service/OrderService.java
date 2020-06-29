package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.bookstore.model.Order;

public interface OrderService {

	public ResponseEntity<Object> makeOrder(Long id,int quantity,Long userId);
}
