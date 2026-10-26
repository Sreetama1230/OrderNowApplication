package com.app.auth.dto.error;

import org.springframework.http.HttpStatus;

public class ErrorObject {

	private String description;
	private HttpStatus status;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public ErrorObject() {
		super();
	}

	public ErrorObject(String description, HttpStatus status) {
		super();
		this.description = description;
		this.status = status;
	}

}
