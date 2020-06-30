package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.Quantity;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepo bookRepository;
	@Autowired
	private OrderRepo orderRepository;
	@Autowired
	private MailTempletService mailTempletService;

	private Logger log = LoggerFactory.getLogger(UserServiceImp.class);

	@Override
	public ResponseEntity<Object> makeOrder(Long bookId, int quantity, Long userId) {
		Book book = bookRepository.findByBookId(bookId);
		Order order = new Order();
		order.setBookId(bookId);
		order.setUserId(userId);
		order.setQuantity(quantity);
		order.setBookName(book.getBookName());
		order.setPrice(book.getPrice());
		order.setTotal(order.getPrice() * order.getQuantity());
		orderRepository.addOrder(order);
		book.setQuantity(book.getQuantity() - 1);
		System.out.println("Added successfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(202, "Order Added to cart"));

	}

}
