package io.github.palharesricardo.clientejavaapi.enumeration;

/**
 * Enum that represents the two types of roles in the API: ROLE_ADMIN and ROLE_USER.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public enum RoleEnum {
	
	ROLE_ADMIN("ROLE_ADMIN"), 
	ROLE_USER("ROLE_USER");
	
	private String value;
	
	private RoleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
