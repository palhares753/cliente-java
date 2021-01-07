package io.github.palharesricardo.clientejavaapi.service.security.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.palharesricardo.clientejavaapi.model.security.JwtUserFactory;
import io.github.palharesricardo.clientejavaapi.model.user.User;
import io.github.palharesricardo.clientejavaapi.service.user.UserService;

/**
 * Class that implements UserDetailsService interface which loads user-specific data.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @see UserDetailsService#loadUserByUsername(String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userService.findByEmail(username);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("User/Email not found.");
	}

}
