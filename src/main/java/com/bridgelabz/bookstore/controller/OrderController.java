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
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value = "/checkout/{bookId}/{quantity}")
	public ResponseEntity<Response> checkOut(@PathVariable("bookId") long bookId,
			@PathVariable("quantity") int quantity, @RequestHeader("token") String token) {
			Order order=orderService.checkOut(bookId, quantity, token);
		if (order!=null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.ORDER_PLACED_SUCCESSFULLY, Constant.OK_RESPONSE_CODE,order));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.ORDER_PLACED_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

}
