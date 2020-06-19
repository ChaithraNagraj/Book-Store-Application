package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookservice;

	@GetMapping(value = "/{authorName}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> searchBookByAuthorName(@PathVariable("authorName") String authorName) {
		List<Book> bookList = bookservice.findBookByAuthorName(authorName);
		return bookList;

	}

	@GetMapping(value = "/{bookName}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> searchBookByBookName(@PathVariable("bookName") String bookName) {
		List<Book> bookList = bookservice.findBookByTitle(bookName);
		return bookList;

	}

	@GetMapping(value = "/getBooks")
	public List<Book> getAllBooks() {
		List<Book> bookList = bookservice.findAllBook();
		return bookList;

	}

	@PostMapping("/addBook/{token}")
	public void addBook(@RequestBody BookDto request, @PathVariable("token") Long userId) {
		System.out.println("check: "+request.getBookName());
		bookservice.addBook(request, userId);
	}

}
