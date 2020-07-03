package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

<<<<<<< HEAD
	@PostMapping(value = "/placeorder/{bookId}/{quantity}")
	public ResponseEntity<Response> makeOrder(@PathVariable("bookId") long bookId,
			@PathVariable("quantity") int quantity, @RequestHeader("token") String token) {
		if (orderService.makeOrder(bookId, quantity, token)) {
=======
	@PostMapping(value = "/checkout/{bookId}/{quantity}")
	public ResponseEntity<Response> checkOut(@PathVariable("bookId") long bookId,
			@PathVariable("quantity") int quantity, @RequestHeader("token") String token) {
		if (orderService.checkOut(bookId, quantity, token)) {
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.ORDER_PLACED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ORDER_PLACED_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

<<<<<<< HEAD
}
=======
}
>>>>>>> 744ddf309893dec48046693bef9ca75b0460b76e
