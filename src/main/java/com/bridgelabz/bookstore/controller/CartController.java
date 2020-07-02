package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.CartBooks;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;

@RestController
@RequestMapping(value = "/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping(value = "/addToCart/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> addtocart(@RequestHeader("token") String token,
			@PathVariable("bookId") long bookId,BindingResult result) {
		Cart cart = cartService.addtocart(token, bookId);
		if (cart != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_ADD_TO_CART, Constant.OK_RESPONSE_CODE, cart));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADD_TO_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@GetMapping("/displayItems")
	public ResponseEntity<Response> displayItems(@RequestHeader("token") String token) {
		List<Book> books = cartService.displayItems(token);
		if (!books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOKS_DISPLAYING_MESSAGE, Constant.OK_RESPONSE_CODE, books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOKS_DISPLAYING_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@PutMapping("/addQuantity/{cartBookId}")
	public ResponseEntity<Response> addQuantity(@RequestHeader("token") String token,
			@PathVariable("cartBookId") long cartBookId) {
		CartBooks cartBook = cartService.addQuantity(cartBookId, token);
		if (cartBook != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.QUANTITY_INCREASED_SUCCESS_MESSAGE, Constant.OK_RESPONSE_CODE, cartBook));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.QUANTITY_INCREASED_FAILED_MESSAGE, Constant.OK_RESPONSE_CODE));
	}

	@PutMapping("/removeQuantity/{cartBookId}")
	public ResponseEntity<Response> removeQuantity(@RequestHeader("token") String token,
			@PathVariable("cartBookId") long cartBookId) {
		CartBooks cartBook = cartService.removeQuantity(cartBookId, token);
		if (cartBook != null) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new Response(Constant.QUANTITY_DECREASED_SUCCESS_MESSAGE, Constant.OK_RESPONSE_CODE, cartBook));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(Constant.QUANTITY_DECREASED_FAILED_MESSAGE, Constant.OK_RESPONSE_CODE));
	}

	@DeleteMapping("/removeFromCart/{cartBookId}")
	public ResponseEntity<Response> removeFromCart(@RequestHeader String token, @PathVariable long cartBookId) {
		boolean status = cartService.removeBookFromCart(token, cartBookId);
		if (status) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_REMOVED_FROM_CART, Constant.OK_RESPONSE_CODE));
		} else
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new Response(Constant.BOOK_REMOVAL_FROM_CART_FAILED, Constant.BAD_REQUEST_RESPONSE_CODE));
	}
}
