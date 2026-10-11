package com.app.order.events;

import java.util.List;

import com.app.order.model.Order;
import com.app.order.model.OrderItem;

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

	public static OrderEvent convertFromOrderToOrderEvent(Order order) {
		OrderEvent event = new OrderEvent();

		List<OrderItemEvent> itemEvents = order.getItems().stream().map(e -> OrderEvent.convertToOrderItemEvent(e))
				.toList();

		event.setItems(itemEvents);
		event.setOrderId(order.getOrderId());
		event.setUserId(order.getUserId());
		return event;

	}

	public static OrderItemEvent convertToOrderItemEvent(OrderItem item) {

		return new OrderItemEvent(item.getProductId(), item.getQuantity());
	}
}
