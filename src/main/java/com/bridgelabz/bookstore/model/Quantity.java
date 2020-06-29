package com.bridgelabz.bookstore.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Quantity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quantityId;
	@Column

	private Long quantityOfBook;
	@Column
	private Double totalprice;

	public long getQuantityId() {
		return quantityId;
	}

	public void setQuantityId(long quantityId) {
		this.quantityId = quantityId;
	}

	public Long getQuantityOfBook() {
		return quantityOfBook;
	}

	public void setQuantityOfBook(Long quantityOfBook) {
		this.quantityOfBook = quantityOfBook;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

}
