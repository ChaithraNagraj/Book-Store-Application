//package com.bridgelabz.bookstore.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.Min;
//
//
//@Entity
//public class MyOrderItems {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column
//	private long myOrderItemsId;
//
//	@Column(name = "book_quantity", nullable = false, columnDefinition = "int default 1")
//	@Min(value = 1)
//	private int bookQuantity;
//	
//
//	public MyOrderItems() {
//		super();
//	}
//
//	public long getMyOrderItemsId() {
//		return myOrderItemsId;
//	}
//
//	public void setMyOrderItemsId(long myOrderItemsId) {
//		this.myOrderItemsId = myOrderItemsId;
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
//	
//}
