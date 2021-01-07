package io.github.palharesricardo.clientejavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.palharesricardo.clientejavaapi.model.user.UserAccount;
import io.github.palharesricardo.clientejavaapi.repository.user.UserAccountRepository;
import io.github.palharesricardo.clientejavaapi.service.user.UserAccountService;

/**
 * Class that implements the user's account service methods
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	UserAccountRepository repository;
	
	/**
	 * @see UserAccountService#save(UserAccount)
	 */
	@Override
	public UserAccount save(UserAccount userTransaction) {
		return repository.save(userTransaction);
	}

	/**
	 * @see UserAccountService#findByUserIdAndAccountId(Long, Long)
	 */
	@Override
	public Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account) {
		return repository.findByUserIdAndAccountId(user, account);
	}

}
