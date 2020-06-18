
package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.bookstore.model.dto.BookDto;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookid;

	@Column
	private String bookName;

	@Column
	private int quantity;

	@Column
	private Double price ;

	@Column
	private String authorName;

	@Column(name = "created_date_time")
	@NotNull
	private LocalDateTime createdDateAndTime;

	@Column(name = "lastupdated_date_time")
	@NotNull
	private LocalDateTime LastUpdatedDateAndTime;

	@Column(name = "verified_date_time")
	@NotNull
	private LocalDateTime  verifiedDateAndTime;
	
	@Column
	private int noOfRejections;
	
	@Column
	private String image;

	@Column(name = "is_approved")
	@NotNull
	private boolean isapproved;
	
	


	
	
	
	
	public Book(Long bookid, String bookName, int quantity, Double price, String authorName,
			@NotNull LocalDateTime createdDateAndTime, @NotNull LocalDateTime lastUpdatedDateAndTime,
			@NotNull LocalDateTime verifiedDateAndTime, int noOfRejections, String image,
			@NotNull boolean isapproved) {
		super();
		this.bookid = bookid;
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.authorName = authorName;
		this.createdDateAndTime = createdDateAndTime;
		this.LastUpdatedDateAndTime = lastUpdatedDateAndTime;
		this.verifiedDateAndTime = verifiedDateAndTime;
		this.noOfRejections = noOfRejections;
		this.image = image;
		this.isapproved = isapproved;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getBookid() {
		return bookid;
	}

	public void setBookid(Long bookid) {
		this.bookid = bookid;
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
		return LastUpdatedDateAndTime;
	}

	public void setLastUpdatedDateAndTime(LocalDateTime lastUpdatedDateAndTime) {
		LastUpdatedDateAndTime = lastUpdatedDateAndTime;
	}

	public LocalDateTime getVerifiedDateAndTime() {
		return verifiedDateAndTime;
	}

	public void setVerifiedDateAndTime(LocalDateTime verifiedDateAndTime) {
		this.verifiedDateAndTime = verifiedDateAndTime;
	}

	public int getNoOfApprovals() {
		return noOfRejections;
	}

	public void setNoOfApprovals(int noOfRejections) {
		this.noOfRejections = noOfRejections;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isIsapproved() {
		return isapproved;
	}

	public void setIsapproved(boolean isapproved) {
		this.isapproved = isapproved;
	}

	

}
