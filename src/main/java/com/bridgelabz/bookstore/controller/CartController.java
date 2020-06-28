package com.bridgelabz.bookstore.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = { "/cart" })
public class CartController {
	
	public CartController() {
		System.out.println("cart controller working");
	}
	@Autowired
	CartService cartService;
	
	
	@PostMapping(value="/addatocart", headers = "Accept=application/json")
	public ResponseEntity<Response> addtocart(@RequestHeader String token,@PathVariable long bookId) {
		boolean value = cartService.addtocart(token, bookId);
		if (value) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_ADD_TO_CART,Constant.OK_RESPONSE_CODE));
		} else
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_ADD_TO_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));

	}

	

}

