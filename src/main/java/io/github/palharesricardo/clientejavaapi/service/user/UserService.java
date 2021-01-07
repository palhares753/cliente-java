package io.github.palharesricardo.clientejavaapi.service.user;

import java.util.Optional;

import io.github.palharesricardo.clientejavaapi.model.user.User;

/**
 * Interface that provides methods for manipulating User objects.
 * 
 */
public interface UserService {

	/**
	 * Method that saves an user in the database.
	 * 
	 * 
	 * @param user
	 * @return User object
	 */
	User save(User user);
	
	/**
	 * Method that find an user by email in the database.
	 * 
	 * 
	 * @param email
	 * @return Optional<User> object
	 */
	Optional<User> findByEmail(String email);
}
