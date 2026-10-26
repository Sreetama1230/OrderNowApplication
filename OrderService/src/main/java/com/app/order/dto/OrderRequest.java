package com.app.order.dto;


import java.util.List;

import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.model.Order;
import com.app.order.model.OrderItem;
import com.app.order.model.OrderStatus;

public class OrderRequest {

	private List<OrderItemRequest> items;



	public List<OrderItemRequest> getItems() {
		return items;
	}



	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}


	public static Order convertToOrder( JwtUserPrincipal jwtUserPrincipal, 
			List<OrderItem> orderItems ){
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PAYMENT_PENDING);
		order.setItems(orderItems);
		double totalAmount = 0;
		for(OrderItem orderItem : orderItems) {
			totalAmount += orderItem.getUnitPrice() * orderItem.getQuantity();
		}	

		order.setTotalAmount(totalAmount);
		order.setUserId(jwtUserPrincipal.getUserId());
		return order;
	}
	



}
