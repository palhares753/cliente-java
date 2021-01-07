package io.github.palharesricardo.clientejavaapi.controller.v1.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.palharesricardo.clientejavaapi.controller.v1.travel.TravelController;
import io.github.palharesricardo.clientejavaapi.dto.model.user.UserAccountDTO;
import io.github.palharesricardo.clientejavaapi.dto.response.Response;
import io.github.palharesricardo.clientejavaapi.model.user.UserAccount;
import io.github.palharesricardo.clientejavaapi.service.user.UserAccountService;
import io.github.palharesricardo.clientejavaapi.util.TravelsApiUtil;

/**
 * SpringBoot RestController that implements all API service end-points related to the 
 * user account that the user's have.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@RestController
@RequestMapping("/api-travels/v1/users-accounts")
public class UserAccountController {

	@Autowired
	private UserAccountService service;
	
	/**
	 * Method that creates an association with the user and a account in the Travels API.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param dto
	 * @param result
	 * @return ResponseEntity with a Response<UserAccountDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 401 - Unauthorized: No valid API key provided.
	 * 403 - Forbidden: The API key doesn't have permissions to perform the request.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 */
	@PostMapping
	public ResponseEntity<Response<UserAccountDTO>> create(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, 
		defaultValue="${api.version}") String apiVersion, @Valid @RequestBody UserAccountDTO dto, BindingResult result) {
		
		Response<UserAccountDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		UserAccount userAccount = service.save(dto.convertDTOToEntity());
		UserAccountDTO userAccountDTO = userAccount.convertEntityToDTO();
		
		//Self link - User Account
		createSelfLink(userAccount, userAccountDTO);
		
		//Relationship link - User
		createUserRelLink(userAccount, userAccountDTO);
		
		//Relationship link - Account
		createAccountRelLink(userAccount, userAccountDTO);
		
		response.setData(userAccountDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}

	/**
	 * Method that creates a self link to UserAccount object
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param userAccount
	 * @param userAccountDTO
	 */
	private void createSelfLink(UserAccount userAccount, UserAccountDTO userAccountDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(UserAccountController.class)
				.slash(userAccount.getId()).withSelfRel();
		userAccountDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a relationship link to User Account object
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param userAccount
	 * @param userAccountDTO
	 */
	private void createAccountRelLink(UserAccount userAccount, UserAccountDTO userAccountDTO) {
		Link relTransactionLink = WebMvcLinkBuilder.linkTo(TravelController.class)
				.slash(userAccount.getAccount().getId()).withRel("account");
		userAccountDTO.add(relTransactionLink);
	}

	/**
	 * Method that creates a relationship link to User object
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param userTransaction
	 * @param userTransactionDTO
	 */
	private void createUserRelLink(UserAccount userTransaction, UserAccountDTO userTransactionDTO) {
		Link relUserLink = WebMvcLinkBuilder.linkTo(UserController.class)
				.slash(userTransaction.getUser().getId()).withRel("user");
		userTransactionDTO.add(relUserLink);
	}
}
