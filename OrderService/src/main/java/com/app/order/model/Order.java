package com.app.order.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	@Column
	private long userId;

	@ElementCollection
	@CollectionTable(

			name = "order_items", joinColumns = @JoinColumn(name = "order_id"))

	private List<OrderItem> items;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	
	@Column
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

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(long userId, List<OrderItem> items, OrderStatus orderStatus, double totalAmount) {
		super();
		this.userId = userId;
		this.items = items;
		this.orderStatus = orderStatus;
		this.totalAmount = totalAmount;
	}


	

}
