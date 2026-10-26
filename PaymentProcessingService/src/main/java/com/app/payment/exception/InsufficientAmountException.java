package com.app.payment.exception;

public class InsufficientAmountException extends RuntimeException {

	public InsufficientAmountException(String message) {
		super(message);
	}
}
