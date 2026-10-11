package com.app.product.exception;

public class InvalidProductOrQuantityException extends RuntimeException{

	public InvalidProductOrQuantityException(String message) {
		super(message);
	}
}
