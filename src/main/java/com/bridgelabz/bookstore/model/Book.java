package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private long bookId;
	@Column(name = "book_code")
	private long bookCode;
	@Column(name = "book_name")
	private String bookName;
	@Column(name = "author_name")
	private String authorName;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "created_date_time")
	private LocalDateTime createdDateTime;
	@Column(name = "updated_date_time")
	private LocalDateTime updatedDateTime;

	public Book() {
		super();
	}

	public Book(String bookName, String authorName, int quantity, LocalDateTime createdDateTime,
			LocalDateTime updatedDateTime) {
		super();
		this.bookName = bookName;
		this.authorName = authorName;
		this.quantity = quantity;
		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
