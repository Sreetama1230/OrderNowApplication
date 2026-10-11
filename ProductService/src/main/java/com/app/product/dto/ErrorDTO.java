package com.app.product.dto;

import org.springframework.http.HttpStatus;

public class ErrorDTO {

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

	public ErrorDTO(String description, HttpStatus httpStatus) {
		super();
		this.description = description;
		this.httpStatus = httpStatus;
	}

}
