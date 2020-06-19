package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.service.BookService;

@RestController

public class BookController {

	@Autowired
	private BookService bookservice;


	@GetMapping(value = "/bookStoreApplication/getBookByAuthorName")
	public List<Book> searchBookByAuthorName(@RequestParam("authorName") String authorName) {
		return bookservice.findBookByAuthorName(authorName);

	}

	@GetMapping(value = "/bookStoreApplication/getBookByName")
	public List<Book> searchBookByBookName(@RequestParam("bookName") String bookName) {
		return bookservice.findBookByTitle(bookName);

	}

	@GetMapping(value = "/getBooks")
	public List<Book> getAllBooks() {
		return bookservice.findAllBook();
	}

	@PostMapping("/addBook/{token}")
	public void addBook(@RequestBody BookDto request, @PathVariable("token") Long userId) {
		bookservice.addBook(request, userId);
	}
}
