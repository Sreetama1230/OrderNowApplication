package com.app.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.auth.dto.error.ErrorObject;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleUserCreationError(UserCreationError creationError){
		return new ResponseEntity<>(new ErrorObject(creationError.getMessage(), HttpStatus.BAD_REQUEST ) , HttpStatus.BAD_REQUEST);
	}
}
