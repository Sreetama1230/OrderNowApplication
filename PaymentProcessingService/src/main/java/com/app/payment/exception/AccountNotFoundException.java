package com.app.payment.exception;

public class AccountNotFoundException extends RuntimeException{

	public AccountNotFoundException(String message){
		super(message);
	}
}
