package com.app.payment.service;

import java.util.Optional;


import org.springframework.stereotype.Service;

import com.app.payment.dao.AccountRepository;
import com.app.payment.dto.AccountRequest;
import com.app.payment.exception.AccountNotFoundException;
import com.app.payment.jwt.JwtUserPrincipal;
import com.app.payment.model.Account;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	AccountService(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
	}
	
	public Account createAccount(AccountRequest accountRequest , JwtUserPrincipal jwtUserPrincipal) {
		Account account = AccountRequest.convertToAccount(accountRequest);
		account.setUserId(jwtUserPrincipal.getUserId());
		return accountRepository.save(account);
	}
	
	public Account updateAccount(Account account) {
		return accountRepository.save(account);
	}
	


	
	public Account getByAccountIdAndUserId(long accountId, long userId) {
		Optional<Account> optionalAccount = accountRepository.findByAccountIdAndUserId(accountId, userId);
		if(optionalAccount.isPresent()) {
			return optionalAccount.get();
		}
		throw new AccountNotFoundException("Invalid account id!");
	}
}
