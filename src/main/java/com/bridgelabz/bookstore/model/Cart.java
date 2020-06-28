package com.bridgelabz.bookstore.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartId;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id") 
	User userId;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "cart_has_books", joinColumns = { @JoinColumn(name = "cart_id") },
	inverseJoinColumns = {
	@JoinColumn(name = "book_id") })
	public List<Book> bookId;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public List<Book> getBookId() {
		return bookId;
	}

	public void setBookId(List<Book> bookId) {
		this.bookId = bookId;
	}
	

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", userId=" + userId + ", bookId=" + bookId + "]";
	}
	

}

