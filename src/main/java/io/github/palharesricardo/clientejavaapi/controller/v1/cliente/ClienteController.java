package io.github.palharesricardo.clientejavaapi.controller.v1.cliente;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.palharesricardo.clientejavaapi.dto.model.cliente.clienteDTO;
import io.github.palharesricardo.clientejavaapi.dto.response.Response;
import io.github.palharesricardo.clientejavaapi.exception.NotParsableContentException;
import io.github.palharesricardo.clientejavaapi.exception.clienteInvalidUpdateException;
import io.github.palharesricardo.clientejavaapi.exception.clienteNotFoundException;
import io.github.palharesricardo.clientejavaapi.model.cliente.cliente;
import io.github.palharesricardo.clientejavaapi.service.cliente.clienteService;
import io.github.palharesricardo.clientejavaapi.util.clientesApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * SpringBoot RestController that creates all service end-points related to the cliente.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@Log4j2
@RestController
@RequestMapping("/api-clientes/v1/clientes")
public class clienteController {
	
	clienteService clienteService;
	
	@Autowired
	public clienteController(clienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	/**
	 * Method that creates clientes in the database.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<clienteDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws NotParsableContentException
	 * @throws BadRequestException 
	 */
	@PostMapping
	@ApiOperation(value = "Route to create clientes")
	public ResponseEntity<Response<clienteDTO>> create(@RequestHeader(value=clientesApiUtil.HEADER_clienteS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=clientesApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody clienteDTO dto, BindingResult result) 
					throws NotParsableContentException {
		
		Response<clienteDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(!clientesApiUtil.isclienteDTOEndDateGreaterThanStartDate(dto)) {
			throw new NotParsableContentException("The cliente's start date is greater than cliente's end date.");
		}
		
		cliente cliente = dto.convertDTOToEntity(); 
		cliente clienteToCreate = clienteService.save(cliente);

		clienteDTO dtoSaved = clienteToCreate.convertEntityToDTO();
		createSelfLink(clienteToCreate, dtoSaved);

		response.setData(dtoSaved);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(clientesApiUtil.HEADER_clienteS_API_VERSION, apiVersion);
		headers.add(clientesApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Method that updates clientes in the database.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * - id - trip id; 
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<clienteDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws clienteNotFoundException
	 * @throws clienteInvalidUpdateException
	 * @throws NotParsableContentException 
	 */
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Route to update a trip")
	public ResponseEntity<Response<clienteDTO>> update(@RequestHeader(value=clientesApiUtil.HEADER_clienteS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=clientesApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody clienteDTO dto, BindingResult result) 
		throws clienteNotFoundException, clienteInvalidUpdateException, NotParsableContentException {
		
		Response<clienteDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if(!clientesApiUtil.isclienteDTOEndDateGreaterThanStartDate(dto)) {
			throw new NotParsableContentException("The cliente's start date is greater than cliente's end date.");
		}

		cliente clienteToFind = clienteService.findById(dto.getId());
		if (clienteToFind.getId().compareTo(dto.getId()) != 0) {
			throw new clienteInvalidUpdateException("You don't have permission to change the cliente id=" + dto.getId());
		}

		cliente cliente = dto.convertDTOToEntity();
		cliente clienteToUpdate = clienteService.save(cliente);
		
		clienteDTO itemDTO = clienteToUpdate.convertEntityToDTO();
		createSelfLink(clienteToUpdate, itemDTO);
		response.setData(itemDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(clientesApiUtil.HEADER_clienteS_API_VERSION, apiVersion);
		headers.add(clientesApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	/**
	 * Method that search a cliente by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param clienteId - the id of the cliente
	 * @param fields - cliente fields that should be returned in JSON as Partial Response
	 * 
	 * @return ResponseEntity with a <code>Response<clienteDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws clienteNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Route to find a trip by your id in the API")
	public ResponseEntity<Response<clienteDTO>> findById(@RequestHeader(value=clientesApiUtil.HEADER_clienteS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=clientesApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @PathVariable("id") Long clienteId,
		@RequestParam(required = false) String fields) throws clienteNotFoundException {
		
		Response<clienteDTO> response = new Response<>();
		cliente cliente = clienteService.findById(clienteId);
		
		clienteDTO dto = cliente.convertEntityToDTO();
		
		if(fields != null) {
			dto = clienteService.getPartialJsonResponse(fields, dto);
		}
		
		createSelfLink(cliente, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(clientesApiUtil.HEADER_clienteS_API_VERSION, apiVersion);
		headers.add(clientesApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that delete a unique trip.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param clienteId - the id of the cliente
	 * 
	 * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 204 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws clienteNotFoundException 
	 */
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Route to delete a trip in the API")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=clientesApiUtil.HEADER_clienteS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=clientesApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("id") Long clienteId) throws clienteNotFoundException {
		
		Response<String> response = new Response<>();
		cliente cliente = clienteService.findById(clienteId);
		
		clienteService.deleteById(cliente.getId());
		response.setData("cliente id=" + cliente.getId() + " successfully deleted");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(clientesApiUtil.HEADER_clienteS_API_VERSION, apiVersion);
		headers.add(clientesApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
}
