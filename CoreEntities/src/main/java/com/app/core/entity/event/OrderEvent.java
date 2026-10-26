package com.app.core.entity.event;

import java.util.List;



public class OrderEvent {
	private long orderId;
	private long userId;
	private List<OrderItemEvent> items;
	private double totalAmount;
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<OrderItemEvent> getItems() {
		return items;
	}

	public void setItems(List<OrderItemEvent> items) {
		this.items = items;
	}

	

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	
	public OrderEvent(long orderId, long userId, List<OrderItemEvent> items, double totalAmount) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.items = items;
		this.totalAmount = totalAmount;
	}

	public OrderEvent() {
		super();
	}


}
