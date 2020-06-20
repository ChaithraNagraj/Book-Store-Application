package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(value = "/addBook/{token}", headers = "Accept=application/json")
	public ResponseEntity<Response> addBook(@RequestBody BookDto newBook, @PathVariable("token") String token) {
		Book addedbook = sellerService.addBook(newBook, token);
		if(addedbook!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Book Added Successfully", HttpStatus.CREATED.value(), addedbook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Book Adding Failed", HttpStatus.BAD_REQUEST.value()));
	}
	
	@PostMapping(value = "/updateBook/{bookId}/{token}",headers="Accept=application/json")
	public ResponseEntity<Response> updateBook(@RequestBody BookDto updatedBookInfo,@PathVariable long bookId,@PathVariable("token") String token){
		Book updatedBook = sellerService.updateBook(updatedBookInfo,bookId,token);
		if(updatedBook!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Book Updation Successfull",HttpStatus.ACCEPTED.value(),updatedBook));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Book Updation Failed",HttpStatus.NOT_FOUND.value(),updatedBook));
	}
	
	@GetMapping(value = "/displayBooks/{token}")
	public ResponseEntity<Response> displayBooks(@PathVariable("token") String token){
		List<Book> sellerBooks = sellerService.getAllBooks(token);
		if(!sellerBooks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Books Found", HttpStatus.OK.value(), sellerBooks));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Your Inventory Is Empty,No Books Found", HttpStatus.NOT_FOUND.value()));
	}
	
	@DeleteMapping(value = "/removeBook/{bookId}/{token}")
	public ResponseEntity<Response> removeBook(@PathVariable("bookId") long bookId,@PathVariable("token") String token){
		boolean isDeleted = sellerService.removeBook(bookId,token);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Book Removed from your Inventory",HttpStatus.OK.value()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Book Not Available In your Inventory",HttpStatus.NOT_FOUND.value()));
	}
}
