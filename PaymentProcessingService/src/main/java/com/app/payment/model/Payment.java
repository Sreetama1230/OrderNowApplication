package com.app.payment.model;

import java.time.LocalDateTime;

import com.app.payment.enums.PaymentStatus;

public class Payment {

	private long paymentId;
	private long orderId;
	private long amount;
	private PaymentStatus status;
	private LocalDateTime createdAt;
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Payment() {
		super();
	}
	public Payment(long paymentId, long orderId, long amount, PaymentStatus status, LocalDateTime createdAt) {
		super();
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	
}
