package com.app.order.model;

import com.app.order.dto.OrderItemRequest;
import com.app.order.dto.ProductDTO;
import com.app.order.jwt.JwtUserPrincipal;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItem {

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
	
	
	public OrderItem(long productId, long quantity, double unitPrice) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	public OrderItem() {
		super();

	}
	
	public static OrderItem convertToOrderItem(OrderItemRequest orderItemRequest ,  JwtUserPrincipal principal, ProductDTO productDTO) {
		return new OrderItem(orderItemRequest.getProductId() , orderItemRequest.getQuantity(),productDTO.getPrice());
	}
	
}
