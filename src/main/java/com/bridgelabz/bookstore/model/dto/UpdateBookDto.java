package com.bridgelabz.bookstore.model.dto;

public class UpdateBookDto {

	private int quantity;
	private Double price;

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

}
