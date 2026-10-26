package com.app.payment.dto;



import java.math.BigDecimal;

import com.app.core.entity.event.enums.PaymentType;



public class PaymentRequest {

	private long orderId;
	private BigDecimal totalAmount;
	private long accountId;
	private PaymentType paymentType;

	

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}


	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}



	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public PaymentRequest() {
		super();
	}

	public PaymentRequest(long orderId, BigDecimal totalAmount, long accountId, PaymentType paymentType) {
		super();
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.accountId = accountId;
		this.paymentType = paymentType;
	}

}
