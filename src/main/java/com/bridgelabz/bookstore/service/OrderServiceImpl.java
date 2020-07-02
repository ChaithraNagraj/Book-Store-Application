package com.bridgelabz.bookstore.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.RandomUtility;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private OrderRepo orderRepository;

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private MailTempletService mailTempletService;

	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	@Transactional
	public boolean checkOut(Long bookId, int quantity, String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		System.out.println(buyer);
		Book book = bookRepository.findByBookId(bookId);
		System.out.println(book);
		Order order = new Order(book.getBookName(), quantity, book.getPrice(), book.getPrice() * quantity);
		order.setUser(buyer);
		order.setCreatedDateAndTime(DateUtility.today());
		order.setOrderNumber(RandomUtility.getRandomNumber());
		order.setBookImage(book.getImageURL());
		orderRepository.addOrder(order);
		book.setQuantity(book.getQuantity() - quantity);
		System.out.println("Added successfully");
		return true;

	}

}
