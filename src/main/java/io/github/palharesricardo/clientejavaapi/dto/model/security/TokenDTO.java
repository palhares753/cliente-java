package io.github.palharesricardo.clientejavaapi.dto.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class that implements Token data transfer object (DTO)
 * 
 * @author Ricardo Palhares	
 * @since 04/01/2021
 */
@AllArgsConstructor
public class TokenDTO {
	
	@Getter
	private String token;

}
