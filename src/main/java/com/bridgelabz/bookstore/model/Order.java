package com.bridgelabz.bookstore.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "userorder")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "book_name", nullable = false)
	private String bookName;

	@Column(name = "quantity", nullable = false)
	@Min(value = 1)
	private int quantity;

	@Column(name = "price", nullable = false)
	@Min(value = 0)
	private Double price;

	@Column(name = "total", nullable = false)
	private double total;

	@Column(name = "created_date_time", nullable = false)
	private LocalDateTime createdDateAndTime;

	public Order(String bookName, int quantity, Double price, double total) {
		super();
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", bookName=" + bookName + ", quantity=" + quantity + ", price=" + price
				+ ", total=" + total + ", createdDateAndTime=" + createdDateAndTime + "]";
	}

}