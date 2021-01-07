package io.github.palharesricardo.clientejavaapi.enumeration;

/**
 * Enum that classifies the account's type in the API.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 * 
 * @author Ricardo Palhares
 *
 */
public enum AccountTypeEnum {
	
	FREE("FREE"),
	BASIC("BASIC"), 
	PREMIUM("PREMIUM");
	
	private String value;
	
	private AccountTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
