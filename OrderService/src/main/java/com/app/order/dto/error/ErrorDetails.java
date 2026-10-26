package com.app.order.dto.error;

import org.springframework.http.HttpStatus;

public class ErrorDetails {

	private String description;
	private HttpStatus httpStatus;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErrorDetails(String description, HttpStatus httpStatus) {
		super();
		this.description = description;
		this.httpStatus = httpStatus;
	}
	
	
}
