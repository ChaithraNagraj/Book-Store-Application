package com.bridgelabz.bookstore.model.dto;

public class OrderDTO {

	private Double orderPrice;		
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDTO(Double orderPrice, Double discount) {
		super();
		this.orderPrice = orderPrice;
		this.discount = discount;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	private Double discount;
}
