package com.app.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.product.dto.ErrorDTO;

@ControllerAdvice
public class CustomExceptionHandler {

	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException exception) {
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(exception.getMessage(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleInsufficientQuantityException(InsufficientQuantityException exception) {
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(exception.getMessage(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleInvalidProductOrQuantityException(InvalidProductOrQuantityException exception) {
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(exception.getMessage(), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
}
