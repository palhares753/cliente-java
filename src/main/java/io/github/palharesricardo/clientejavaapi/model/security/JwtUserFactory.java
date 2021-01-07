package io.github.palharesricardo.clientejavaapi.model.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.palharesricardo.clientejavaapi.enumeration.RoleEnum;
import io.github.palharesricardo.clientejavaapi.model.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class that implements a factory to create a JwtUser.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserFactory {
	
	/**
	 * Method to create a JwtUser.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param user
	 * @return JwtUser object
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), createGrantedAuthorities(user.getRole()));
	}
	
	/**
	 * Method to grant authorities to a JwtUser.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param role
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> createGrantedAuthorities(RoleEnum role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

}
