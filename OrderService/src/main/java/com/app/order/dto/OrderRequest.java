package com.app.order.dto;

import java.util.List;

import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.model.Order;
import com.app.order.model.OrderItem;
import com.app.order.model.OrderStatus;

public class OrderRequest {

	private List<OrderItem> items;

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public OrderRequest(List<OrderItem> items) {
		super();
		this.items = items;
	}

	public static Order convertToOrder(OrderRequest orderRequest, JwtUserPrincipal jwtUserPrincipal) {

		Order order = new Order();
		order.setItems(orderRequest.getItems());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUserId(jwtUserPrincipal.getUserId());
		return order;
	}

}
