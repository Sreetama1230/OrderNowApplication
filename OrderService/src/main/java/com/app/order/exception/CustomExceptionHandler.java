package com.app.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.order.dto.error.ErrorDetails;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorDetails> handleProductDetailsInvalid(ProductDetailsInvalid productDetailsInvalid) {
		return new ResponseEntity<ErrorDetails>(
				new ErrorDetails(productDetailsInvalid.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDetails> handleOrderNotFoundException(OrderNotFoundException productDetailsInvalid) {
		return new ResponseEntity<ErrorDetails>(
				new ErrorDetails(productDetailsInvalid.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	}
}
