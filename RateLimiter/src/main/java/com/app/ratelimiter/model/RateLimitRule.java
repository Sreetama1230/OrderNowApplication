package com.app.ratelimiter.model;

public class RateLimitRule {
	
	private String ruleId;
	private ClientType clientType;
	private int capacity;
	private double refillRate;
	private long refillIntervalSeconds;
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public ClientType getClientType() {
		return clientType;
	}
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public double getRefillRate() {
		return refillRate;
	}
	public void setRefillRate(double refillRate) {
		this.refillRate = refillRate;
	}
	public long getRefillIntervalSeconds() {
		return refillIntervalSeconds;
	}
	public void setRefillIntervalSeconds(long refillIntervalSeconds) {
		this.refillIntervalSeconds = refillIntervalSeconds;
	}
	
	
	

	
}
