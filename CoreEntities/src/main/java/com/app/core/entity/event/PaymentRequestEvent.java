package com.app.core.entity.event;

import java.math.BigDecimal;

import com.app.core.entity.event.enums.PaymentType;

public class PaymentRequestEvent {

	private long orderId;
	private long accountId;
	private long userId;
	private PaymentType paymentType;
	private BigDecimal totalAmount;
	private String uniqueKey;
	
	
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public PaymentRequestEvent(long orderId, long accountId, long userId, PaymentType paymentType,
			BigDecimal totalAmount) {
		super();
		this.orderId = orderId;
		this.accountId = accountId;
		this.userId = userId;
		this.paymentType = paymentType;
		this.totalAmount = totalAmount;
	}

	public PaymentRequestEvent() {
		super();
	}

}
