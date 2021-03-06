package io.github.palharesricardo.clientejavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.palharesricardo.clientejavaapi.model.user.User;
import io.github.palharesricardo.clientejavaapi.repository.user.UserRepository;
import io.github.palharesricardo.clientejavaapi.service.user.UserService;

/**
 * Class that implements the user's service methods
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;

	/**
	 * @see UserService#save(User)
	 */
	@Override
	public User save(User user) {
		return repository.save(user);
	}

	/**
	 * @see UserService#findByEmail(String)
	 */
	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
