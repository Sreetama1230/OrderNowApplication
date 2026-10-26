package com.app.payment.dto;

import java.math.BigDecimal;

import com.app.payment.enums.AccountType;
import com.app.payment.model.Account;

public class AccountRequest {

	private String name;
	private BigDecimal amount;
	private AccountType accountType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public AccountRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountRequest(String name, BigDecimal amount, AccountType accountType) {
		super();
		this.name = name;
		this.amount = amount;
		this.accountType = accountType;
	}
	
	public static Account convertToAccount(AccountRequest accountRequest) {
		return new Account(accountRequest.getAccountType(), accountRequest.getAmount(), accountRequest.getName());
	}
}
