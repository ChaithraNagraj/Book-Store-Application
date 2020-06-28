package com.bridgelabz.bookstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;

@RestController
@RequestMapping(value = "/cart" )
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
	@DeleteMapping("/removeFromCart{/bookId}")
	public ResponseEntity<Response> removeFromCart(@RequestHeader String token,@PathVariable long bookId){
		boolean status = cartService.removeBookFromCart(token, bookId);
		if(status) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response(Constant.BOOK_REMOVED_FROM_CART,Constant.OK_RESPONSE_CODE));
		}
		else
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(Constant.BOOK_REMOVAL_FROM_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	

}

