package com.app.payment.model;

import java.math.BigDecimal;

import com.app.payment.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@Column(nullable = false)
	private BigDecimal amount;
	@Column(nullable = false)
	private long userId;

	@Column(unique = true)
	private String name;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Account() {
		super();
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account(AccountType accountType, BigDecimal amount, long userId, String name) {
		super();
		this.accountType = accountType;
		this.amount = amount;
		this.userId = userId;
		this.name = name;
	}

	public Account(AccountType accountType, BigDecimal amount, String name) {
		super();
		this.accountType = accountType;
		this.amount = amount;
		this.name = name;
	}

}
