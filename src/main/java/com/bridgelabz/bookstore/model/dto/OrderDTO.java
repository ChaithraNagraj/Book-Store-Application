package com.bridgelabz.bookstore.model.dto;

public class OrderDTO {

	private Double amount;		
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderDTO(Double amount, Double discount) {
		super();
		this.amount = amount;
		this.discount = discount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	private Double discount;
}
