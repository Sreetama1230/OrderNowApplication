package com.app.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payment.dto.AccountRequest;
import com.app.payment.jwt.JwtUserPrincipal;
import com.app.payment.model.Account;
import com.app.payment.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<Account> createAccount(@RequestBody AccountRequest account , @AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal) {
		return new ResponseEntity<Account>(accountService.createAccount(account , jwtUserPrincipal), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<Account> getByIdAccount(@PathVariable long id , @AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal) {
		return new ResponseEntity<Account>(accountService.getByAccountIdAndUserId(id , jwtUserPrincipal.getUserId()), HttpStatus.OK);
	}
}
