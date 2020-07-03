package com.bridgelabz.bookstore.service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

=======
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
<<<<<<< HEAD
import com.bridgelabz.bookstore.model.Cart;
=======
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
<<<<<<< HEAD
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.bridgelabz.bookstore.utils.MailTempletService;
=======
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.MailTempletService;
import com.bridgelabz.bookstore.utils.RandomUtility;
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepo bookRepository;
<<<<<<< HEAD
	
	@Autowired
	private UserRepo userRepository;
=======
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e

	@Autowired
	private OrderRepo orderRepository;

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private MailTempletService mailTempletService;

	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	@Transactional
<<<<<<< HEAD
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
=======
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
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
		orderRepository.addOrder(order);
		book.setQuantity(book.getQuantity() - quantity);
		System.out.println("Added successfully");
		return true;

	}

<<<<<<< HEAD
}
=======
}
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
