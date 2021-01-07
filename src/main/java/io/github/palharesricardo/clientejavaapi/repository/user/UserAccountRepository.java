package io.github.palharesricardo.clientejavaapi.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.palharesricardo.clientejavaapi.model.user.UserAccount;

/**
 * Interface that implements the User Account Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	/**
	 * Method to search an UserAccount by user's id and account id.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param user
	 * @param account
	 * @return Optional<UserAccount>
	 */
	Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account);
}