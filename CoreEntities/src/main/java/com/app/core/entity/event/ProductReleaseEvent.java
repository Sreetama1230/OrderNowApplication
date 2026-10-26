package com.app.core.entity.event;

import java.util.List;

public class ProductReleaseEvent {

	private long orderId;
	private List<OrderItemEvent> items;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public List<OrderItemEvent> getItems() {
		return items;
	}
	public void setItems(List<OrderItemEvent> items) {
		this.items = items;
	}
	public ProductReleaseEvent() {
		super();
	}
	public ProductReleaseEvent(long orderId, List<OrderItemEvent> items) {
		super();
		this.orderId = orderId;
		this.items = items;
	}

}
