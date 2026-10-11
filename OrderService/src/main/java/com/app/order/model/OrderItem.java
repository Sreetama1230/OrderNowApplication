package com.app.order.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {

	private long productId;
	private long quantity;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public OrderItem(long productId, long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
