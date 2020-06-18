package com.bridgelabz.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.response.Response;

public interface BookService {

	public List<Book> findBookByAuthorName(String authorName);

	public List<Book> findBookByTitle(String title);

	public List<Book> findAllBook();

	public void addBook(BookDto request, Long userId);

}
