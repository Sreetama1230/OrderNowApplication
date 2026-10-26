package com.app.core.entity.event;

public class ProductReservationResultEvent {

	private Long orderId;
	private boolean successful;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ProductReservationResultEvent() {
		super();
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public ProductReservationResultEvent(Long orderId, boolean successful) {
		super();
		this.orderId = orderId;
		this.successful = successful;
	}
	
	
	
}
