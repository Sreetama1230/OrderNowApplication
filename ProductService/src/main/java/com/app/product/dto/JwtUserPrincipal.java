package com.app.product.dto;

public class JwtUserPrincipal {

	private long userId;
	private String username;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public JwtUserPrincipal(long userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}
	
	
}
