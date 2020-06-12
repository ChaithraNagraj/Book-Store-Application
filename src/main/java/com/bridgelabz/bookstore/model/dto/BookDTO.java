package com.bridgelabz.bookstore.model.dto;

public class BookDTO {

	private String bookCode;
	private String bookName;
	private String authorName;
	private String quantity;

	public BookDTO() {
		super();
	}

	public BookDTO(String bookCode, String bookName, String authorName, String quantity) {
		super();
		this.bookCode = bookCode;
		this.bookName = bookName;
		this.authorName = authorName;
		this.quantity = quantity;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	
}
