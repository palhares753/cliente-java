package io.github.palharesricardo.clientejavaapi.service.account;

import java.util.Optional;

import io.github.palharesricardo.clientejavaapi.model.account.Account;

/**
 * Interface that provides methods for manipulating Account objects.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public interface AccountService {
	
	/**
	 * Method that saves an account in the database.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param account
	 * @return <code>Account</code> object
	 */
	Account save(Account account);
	
	/**
	 * Method that find an account by accountNumber in the database.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param accountNumber
	 * @return Optional<Account> object
	 */
	Optional<Account> findByAccountNumber(String accountNumber);
	
	/**
	 * Method that find a account by an id.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param id
	 * @return <code>Optional<Account></code> object
	 */
	Optional<Account> findById(Long accountId);

}
