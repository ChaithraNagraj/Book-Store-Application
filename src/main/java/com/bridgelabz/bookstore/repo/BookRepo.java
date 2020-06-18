package com.bridgelabz.bookstore.repo;

import java.util.List;
import com.bridgelabz.bookstore.model.Book;

public interface BookRepo {

	public List<Book> findBookByAuthorName(String authorName);

	public List<Book> findBookByTitle(String bookName);

	public List<Book> findAllBooks();

}
