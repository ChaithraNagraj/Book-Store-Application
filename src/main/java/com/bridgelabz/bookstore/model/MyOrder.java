//package com.bridgelabz.bookstore.model;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.validation.constraints.Min;
//
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//
//@Entity
//public class MyOrder {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column
//	private long myOrderId;
//
//	public MyOrder() {
//		super();
//	}
//
//	public long getMyOrderId() {
//		return myOrderId;
//	}
//
//	public void setMyOrderId(long myOrderId) {
//		this.myOrderId = myOrderId;
//	}
//
//	public int getBookQuantity() {
//		return bookQuantity;
//	}
//
//	public void setBookQuantity(int bookQuantity) {
//		this.bookQuantity = bookQuantity;
//	}
//
//	@Column(name = "book_quantity", nullable = false, columnDefinition = "int default 1")
//	@Min(value = 1)
//	private int bookQuantity;
//
//	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//	@JoinColumn(name = "user_id")
//	private User user;
//
//	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//	@JoinColumn(name = "book_id")
//	private Book book;
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public Book getBook() {
//		return book;
//	}
//
//	public void setBook(Book book) {
//		this.book = book;
//	}
//
//}
