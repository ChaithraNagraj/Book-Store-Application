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
import com.bridgelabz.bookstore.service.CartService;

@RestController
@RequestMapping(value = "/carts")
public class CartController {

	@Autowired
	CartService cartService;

	@PostMapping(value = "/addToCart/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> addtocart(@RequestHeader("token") String token, @PathVariable("bookId") long bookId) {
		if (cartService.addtocart(token, bookId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_ADD_TO_CART, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADD_TO_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));

	}

}
