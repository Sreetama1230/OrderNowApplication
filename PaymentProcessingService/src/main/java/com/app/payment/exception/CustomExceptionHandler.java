package com.app.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.payment.dto.error.ErrorObject;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleInsufficientAmountException(InsufficientAmountException exception) {
		return new ResponseEntity<ErrorObject>(new ErrorObject(exception.getMessage(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleAccountNotFoundException(AccountNotFoundException exception) {
		return new ResponseEntity<ErrorObject>(new ErrorObject(exception.getMessage(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handlePaymentNotFoundException(PaymentNotFoundException exception) {
		return new ResponseEntity<ErrorObject>(new ErrorObject(exception.getMessage(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleAccountTypeInvalidException(AccountTypeInvalidException exception) {
		return new ResponseEntity<ErrorObject>(new ErrorObject(exception.getMessage(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}
}
