package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;
import com.bridgelabz.bookstore.model.MyOrder;
import com.bridgelabz.bookstore.model.MyOrderItems;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.OrderRepo;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.TokenUtility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private TokenUtility tokenUtility;

	public void addOrder(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		Cart cart = buyer.getUserCart();
		List<MyOrderItems> orders = buyer.getMyOrderItems();
		MyOrder myOrder = new MyOrder();
		myOrder.setBookQuantity(cart.getTotalBooksInCart());
		///myOrder.setCart(orders);
		buyer.getMyOrder().add(myOrder);
		userRepository.addUser(buyer);

		List<CartBooks> cartBooks = cart.getCartBooks();
		for (int i = 0; i < cartBooks.size(); i++) {
			MyOrderItems items = new MyOrderItems();
			items.setBookQuantity(cartBooks.get(i).getBookQuantity());
			Book book = cartBooks.get(i).getBook();
			book.getMyOrderItems().add(items);
			bookRepository.save(book);
			buyer.getMyOrderItems().add(items);
			userRepository.addUser(buyer);
		}

	}

	@Override
	public List<MyOrder> getOrders(String token) {
		User buyer = tokenUtility.authentication(token, Constant.ROLE_AS_BUYER);
		List<MyOrder> myOrder = buyer.getMyOrder();
		return myOrder;
	}

}
