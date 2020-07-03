package com.bridgelabz.bookstore.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.RandomUtility;
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

	@Override
	@Transactional
	public Order checkOut(Long bookId, int quantity, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		System.out.println(buyer);
		Book book = bookRepository.findByBookId(bookId);
		User sellerDetails = userRepository.findByUserId(book.getSeller().getId());
		System.out.println(book);
		Order order = new Order(book.getBookName(), quantity, book.getPrice(), book.getPrice() * quantity);
		order.setUser(buyer);
		order.setCreatedDateAndTime(DateUtility.today());
		order.setOrderNumber(RandomUtility.getRandomNumber());
		order.setBookImage(book.getImageURL());
		order.setAuthor(book.getAuthorName());
		order.setVenderName(sellerDetails.getName());
		order.setBook(book);
		orderRepository.addOrder(order);
		book.setQuantity(book.getQuantity() - quantity);
		System.out.println("Added successfully");
		return order;

	}

	@Override
	public List<Order> myOrder(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		System.out.println(buyer.getId());
		return orderRepository.findMyOrder(buyer.getId());
	}

}
