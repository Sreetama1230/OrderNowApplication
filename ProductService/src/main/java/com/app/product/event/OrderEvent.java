package com.app.product.event;

import java.util.List;

public class OrderEvent {
	private long orderId;
	private long userId;
	private List<OrderItemEvent> items;

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

	public OrderEvent(long orderId, long userId, List<OrderItemEvent> items) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.items = items;
	}

	public OrderEvent() {
		super();
	}

}
