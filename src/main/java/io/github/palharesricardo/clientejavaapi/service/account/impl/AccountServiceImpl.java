package io.github.palharesricardo.clientejavaapi.service.account.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.repository.account.AccountRepository;
import io.github.palharesricardo.clientejavaapi.service.account.AccountService;

/**
 * Class that implements the account service methods
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository repository;

	/**
	 * @see AccountService#save(Account)
	 */
	@Override
	public Account save(Account account) {
		return repository.save(account);
	}

	/**
	 * @see AccountService#findById(Long)
	 */
	@Override
	public Optional<Account> findById(Long accountId) {
		return repository.findById(accountId);
	}

}
