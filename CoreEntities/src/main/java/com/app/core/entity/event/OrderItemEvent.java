package com.app.core.entity.event;

public class OrderItemEvent {

	private long productId;
	private long quantity;
	private double unitPrice;

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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public OrderItemEvent(long productId, long quantity, double unitPrice) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public OrderItemEvent() {
		super();
	}

}
