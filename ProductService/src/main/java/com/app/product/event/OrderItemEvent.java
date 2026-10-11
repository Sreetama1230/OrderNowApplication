package com.app.product.event;

public class OrderItemEvent {
	
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
	public OrderItemEvent(long productId, long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public OrderItemEvent() {
		super();
	}
	
	
	
}
