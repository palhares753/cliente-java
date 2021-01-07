package io.github.palharesricardo.clientejavaapi.enumeration;

/**
 * Enum that classifies the travel's type.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public enum ClienteTypeEnum {
	
	ONE_WAY("ONE-WAY"), RETURN("RETURN"), MULTI_CITY("MULTI-CITY");
	
	private String value;
	
	private ClienteTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
