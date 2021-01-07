package io.github.palharesricardo.clientejavaapi.service.user;

import java.util.Optional;

import io.github.palharesricardo.clientejavaapi.model.user.UserAccount;

/**
 * Interface that provides methods for manipulating User Account objects.
 * 
 */
public interface UserAccountService {
	
	/**
	 * Method that saves an User Account in the database.
	 * 
	 * 
	 * @param userAccount
	 * @return UserAccount object
	 */
	UserAccount save(UserAccount userAccount);
	
	/**
	 * Method that find an UserAccount by user's id and account's id.
	 * 
	 * 
	 * @param user
	 * @param account
	 * @return Optional<UserAccount>
	 */
	Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account);

}
