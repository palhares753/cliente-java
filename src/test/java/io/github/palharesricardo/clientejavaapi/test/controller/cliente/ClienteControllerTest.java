package io.github.palharesricardo.clientejavaapi.test.controller.Cliente;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.palharesricardo.clientejavaapi.dto.model.Cliente.ClienteDTO;
import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.enumeration.ClienteTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.model.Cliente.Cliente;
import io.github.palharesricardo.clientejavaapi.service.Cliente.clienteervice;
import io.github.palharesricardo.clientejavaapi.util.clienteApiUtil;

/**
 * Class that implements tests of the TransactionController features
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClienteControllerTest {
	
	static final Long ID = 1L;
	static final String CPF = "xxxxxxxxxxxx";
	static final String NOME = "";
	static final String ENDERECO = "";
	static final String URL = "/api-cliente/v1/cliente";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	clienteervice clienteervice;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an object Cliente in the API
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(clienteervice.save(Mockito.any(Cliente.class))).willReturn(getMockCliente());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, CPF, NOME, ENDERECO)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.cpf").value(CPF))
		.andExpect(jsonPath("$.data.nome").value(NOME))
		.andExpect(jsonPath("$.data.endereco").value(ENDERECO));
	}
	
	/**
	 * Method that fill a mock Cliente object to use as return in the tests.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>Cliente</code> object
	 * @throws ParseException 
	 */
	private Cliente getMockCliente() throws ParseException {
		
		Cliente Cliente = new Cliente(ID, CPF, NOME, ENDERECO,
				VALUE, TYPE, new Account(1L, "123456", AccountTypeEnum.BASIC));
		return Cliente;
	}
	
	/**
	 * Method that fill a mock ClienteDTO object to use as return in the tests.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param id
	 * @param nsu
	 * @param authorization
	 * @param transactionDate
	 * @param amount
	 * @param type
	 * @return <code>String</code> with the ClienteDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String cpf, String nome, String endereco,
			BigDecimal amount, String type, Long accountId) throws JsonProcessingException {
		
		ClienteDTO dto = new ClienteDTO(id, cpf, nome, endereco);
	        
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	}
	
	@AfterAll
	private void tearDown() {
		clienteervice.deleteById(1L);
	}

}
