package com.app.core.entity.event;

import com.app.core.entity.event.enums.PaymentStatus;

public class PaymentResultEvent {

	private long paymentId;
	private PaymentStatus paymentStatus;
	private String message;
	private long orderId;
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
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
	public PaymentResultEvent(long paymentId, PaymentStatus paymentStatus, String message, long orderId) {
		super();
		this.paymentId = paymentId;
		this.paymentStatus = paymentStatus;
		this.message = message;
		this.orderId = orderId;
	}
	public PaymentResultEvent() {
		super();
	}
	
	
	
	
}
