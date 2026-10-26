package com.app.core.entity.event;

public class OrderCancellationEvent {
    private Long orderId;
    private Long paymentId;
    private String reason;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public OrderCancellationEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderCancellationEvent(Long orderId, Long paymentId, String reason) {
		super();
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.reason = reason;
	}
    
    
}
