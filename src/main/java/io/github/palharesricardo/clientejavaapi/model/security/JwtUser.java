package io.github.palharesricardo.clientejavaapi.model.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class that implements UserDetails from SpringFramework Security Core
 * for authentication purposes.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -8328911063439191378L;
	
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public JwtUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @see UserDetails#getAuthorities()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @see UserDetails#getPassword()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * @see UserDetails#getUsername()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @see UserDetails#isAccountNonExpired()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @see UserDetails#isAccountNonLocked()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @see UserDetails#isCredentialsNonExpired()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @see UserDetails#isEnabled()
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * Method to get the Id from an JwtUser
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return Long - Id
	 */
	public Long getId() {
		return id;
	}

}
