package io.github.palharesricardo.clientejavaapi.controller.v1.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.palharesricardo.clientejavaapi.dto.model.security.JwtUserDTO;
import io.github.palharesricardo.clientejavaapi.dto.model.security.TokenDTO;
import io.github.palharesricardo.clientejavaapi.dto.response.Response;
import io.github.palharesricardo.clientejavaapi.util.clienteApiUtil;
import io.github.palharesricardo.clientejavaapi.util.security.JwtTokenUtil;

/**
 * SpringBoot RestController that implements the API authentication end-points.
 *  
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@RestController
@RequestMapping("/api-cliente/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Method that generates valid JWT tokens to authorize access for API clients.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param dto
	 * @param result
	 * @return ResponseEntity with a Response<TokenDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 401 - Unauthorized: No valid API key provided.
	 * 403 - Forbidden: The API key doesn't have permissions to perform the request.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on Financial Java API end (These are rare).
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDTO>> generateTokenJwt(@RequestHeader(value=clienteApiUtil.HEADER_cliente_API_VERSION, defaultValue = "${api.version}") 
		String apiVersion, @Valid @RequestBody JwtUserDTO dto, BindingResult result) throws AuthenticationException {
		
		Response<TokenDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
		String token = jwtTokenUtil.getToken(userDetails);
		response.setData(new TokenDTO(token));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(clienteApiUtil.HEADER_cliente_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

}
