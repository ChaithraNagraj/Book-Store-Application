package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.SellerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/sellers")
@Api(value = "Seller Controller to perform CRUD operations on book")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@PostMapping(value = "/addBook")
	public ResponseEntity<Response> addBook(@RequestBody BookDto newBook,
			@RequestHeader("token") String token) {
		Book addedbook = sellerService.addBook(newBook, token);
		if (addedbook != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
					Constant.BOOK_ADDITION_SUCCESSFULL_MESSAGE, Constant.CREATED_RESPONSE_CODE, addedbook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(Constant.BOOK_ADDITION_FAILED_MESSAGE, Constant.BAD_REQUEST_RESPONSE_CODE));
	}

	@PutMapping(value = "/updateBook/{bookId}", headers = "Accept=application/json")
	public ResponseEntity<Response> updateBook(@RequestBody BookDto updatedBookInfo, @PathVariable long bookId,
			@RequestHeader("token") String token) {
		Book updatedBook = sellerService.updateBook(updatedBookInfo, bookId, token);
		if (updatedBook != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(
					Constant.BOOK_UPDATION_SUCCESSFULL_MESSAGE, Constant.ACCEPT_RESPONSE_CODE, updatedBook));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new Response(Constant.BOOK_UPDATION_FAILED_MESSAGE, Constant.NOT_FOUND_RESPONSE_CODE, updatedBook));
	}

	@GetMapping(value = "/displayBooks")
	public ResponseEntity<Response> displayBooks(@RequestHeader("token") String token) {
		List<Book> sellerBooks = sellerService.getAllBooks(token);
		if (!sellerBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_FOUND, Constant.OK_RESPONSE_CODE, sellerBooks));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@DeleteMapping(value = "/removeBook/{bookId}")
	public ResponseEntity<Response> removeBook(@PathVariable("bookId") long bookId,
			@RequestHeader("token") String token) {
		boolean isDeleted = sellerService.removeBook(bookId, token);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_DELETION_SUCCESSFULL_MESSAGE, Constant.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE));
	}

	@PutMapping(value = "/addQuantity/{bookId}")
	public ResponseEntity<Response> addQuantity(@PathVariable("bookId") long bookId,
			@RequestHeader("token") String token, @RequestParam("quantity") int quantity) {
		Book book = sellerService.addQuantity(bookId, token, quantity);
		if (book != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response(Constant.BOOK_QUANTITY_ADDITION_SUCCESSFULL, Constant.OK_RESPONSE_CODE, book));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Constant.BOOK_NOT_FOUND, Constant.NOT_FOUND_RESPONSE_CODE, book));
	}

	@PutMapping(value = "/uploadBookImage")
	public ResponseEntity<Response> updateBookImage(@RequestParam("file") MultipartFile image) {
		System.out.println(image.getOriginalFilename());
		String  imageUrl = sellerService.uploadImage(image);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Book image added sucessfully",Constant.OK_RESPONSE_CODE,imageUrl));
	}
}
