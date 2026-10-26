package com.app.order.dto;


import com.app.core.entity.event.enums.PaymentType;

public class OrderCheckOutRequest {

	private long orderId;
	private long accountId;

	private PaymentType paymentType;


	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}


	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}



	public OrderCheckOutRequest() {
		super();
	}

	public OrderCheckOutRequest(long orderId, long accountId,  PaymentType paymentType) {
		super();
		this.orderId = orderId;
		this.accountId = accountId;
		this.paymentType = paymentType;
	}

}
