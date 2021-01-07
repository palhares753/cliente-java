package io.github.palharesricardo.clientejavaapi.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.palharesricardo.clientejavaapi.model.user.User;

/**
 * Interface that implements the User Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
	/**
	 * Method to search an User by the email.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> findByEmail(String email);
}

