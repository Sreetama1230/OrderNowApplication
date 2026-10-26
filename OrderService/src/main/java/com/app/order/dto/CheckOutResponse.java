package com.app.order.dto;

import com.app.core.entity.event.enums.PaymentStatus;

public class CheckOutResponse {

	private long orderId;
	private PaymentStatus paymentStatus;
	private String message;

	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public CheckOutResponse() {
		super();
	}
	public CheckOutResponse(long orderId, PaymentStatus paymentStatus, String message) {
		super();
		this.orderId = orderId;
		this.paymentStatus = paymentStatus;
		this.message = message;
	}
	
	
}
