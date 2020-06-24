
package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	@Column
	@NotNull
	private String bookName;

	@Column
	@NotNull
	private int quantity;

	@Column
	@NotNull
	private Double price;

	@Column
	@NotNull
	private String authorName;

	@Column(name = "created_date_time")
	@NotNull
	private LocalDateTime createdDateAndTime;

	@Column(name = "lastupdated_date_time")
	private LocalDateTime lastUpdatedDateAndTime;

	@Column(name = "verified_date_time")
	private LocalDateTime verifiedDateAndTime;

	@Column
	private int noOfRejections;

	@Column
	private String image;
	
	@Column(length = 10000)
	private String bookDetails;

	@Column(name = "is_approved",columnDefinition = "boolean default false")
	@NotNull
	private boolean isApproved;

	public Book(Long bookid, String bookName, int quantity, Double price, String authorName,
			@NotNull LocalDateTime createdDateAndTime, @NotNull LocalDateTime lastUpdatedDateAndTime,
			@NotNull LocalDateTime verifiedDateAndTime, int noOfRejections, String image, @NotNull boolean isapproved,String bookDetails) {
		super();
		this.bookId = bookid;
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.authorName = authorName;
		this.createdDateAndTime = createdDateAndTime;
		this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
		this.verifiedDateAndTime = verifiedDateAndTime;
		this.noOfRejections = noOfRejections;
		this.image = image;
		this.isApproved = isapproved;
		this.bookDetails = bookDetails;
	}

	public Book() {
		super();
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public LocalDateTime getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}

	public LocalDateTime getLastUpdatedDateAndTime() {
		return lastUpdatedDateAndTime;
	}

	public void setLastUpdatedDateAndTime(LocalDateTime lastUpdatedDateAndTime) {
		this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
	}

	public LocalDateTime getVerifiedDateAndTime() {
		return verifiedDateAndTime;
	}

	public void setVerifiedDateAndTime(LocalDateTime verifiedDateAndTime) {
		this.verifiedDateAndTime = verifiedDateAndTime;
	}

	public int getNoOfRejections() {
		return noOfRejections;
	}

	public void setNoOfRejections(int noOfRejections) {
		this.noOfRejections = noOfRejections;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}


	public String getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}


	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", quantity=" + quantity + ", price=" + price
				+ ", authorName=" + authorName + ", createdDateAndTime=" + createdDateAndTime
				+ ", lastUpdatedDateAndTime=" + lastUpdatedDateAndTime + ", verifiedDateAndTime=" + verifiedDateAndTime

	

				+ ", noOfRejections=" + noOfRejections + ", image=" + image + ", bookDetails=" + bookDetails
				+ ", isApproved=" + isApproved + "]";
	}


}
