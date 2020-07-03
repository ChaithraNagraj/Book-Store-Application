package com.bridgelabz.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepo bookRepository;
	
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private OrderRepo orderRepository;

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private MailTempletService mailTempletService;

	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	@Transactional
	public boolean makeOrder(Long bookId, int quantity, String token) {
		System.out.println("Start------------>");
		Long userId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		System.out.println("---------------------------->"+userId);
		Long roleId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("roleId"));
		System.out.println("---------------------------->"+roleId);
		User buyer = userRepository.findByUserIdAndRoleId(userId, roleId);
		System.out.println(buyer);
		Book book = bookRepository.findByBookId(bookId);
		//System.out.println(book);
		Order order = new Order(book.getBookName(),quantity,book.getPrice(), book.getPrice()*quantity);
		order.setUser(buyer);
		order.setCreatedDateAndTime(DateUtility.today());
		//order.setQuantity(quantity);
		//order.setBookName(book.getBookName());
		//order.setPrice(book.getPrice());
		//order.setTotal(order.getPrice() * order.getQuantity());
		orderRepository.addOrder(order);
		book.setQuantity(book.getQuantity() - quantity);
		System.out.println("Added successfully");
		return true;

	}

}