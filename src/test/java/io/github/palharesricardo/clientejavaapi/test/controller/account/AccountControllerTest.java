package io.github.palharesricardo.clientejavaapi.test.controller.account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import io.github.palharesricardo.clientejavaapi.dto.model.account.AccountDTO;
import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.service.account.AccountService;

/**
 * Class that implements tests of the AccountController features
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
public class AccountControllerTest {
	
	static final String URL = "/api-travels/v1/accounts";
	static final String ACCOUNT_NUMBER = "15649889";
	static final Long ACCOUNT_ID = 1L;

	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AccountService accountService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an Account in the API
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(accountService.save(Mockito.any(Account.class))).willReturn(getMockAccount());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ACCOUNT_ID, ACCOUNT_NUMBER,
				AccountTypeEnum.BASIC.getValue()))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ACCOUNT_ID))
		.andExpect(jsonPath("$.data.accountNumber").value(ACCOUNT_NUMBER))
		.andExpect(jsonPath("$.data.accountType").value(AccountTypeEnum.BASIC.getValue()));
	}
	
	/**
	 * Method that fill a mock Account to use as return in the tests.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @return <code>Account</code> object
	 * @throws ParseException 
	 */
	private Account getMockAccount() throws ParseException {
		return new Account(ACCOUNT_ID, ACCOUNT_NUMBER, AccountTypeEnum.BASIC);
	}
	
	/**
	 * Method that fill a mock AccountDTO to use as return in the tests.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param id
	 * @param accountNumber
	 * @param accountType
	 * @return <code>String</code> with the AccountDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String accountNumber, String accountType) 
			throws JsonProcessingException {
		
		AccountDTO dto = new AccountDTO(id, accountNumber, accountType);
		return new ObjectMapper().writeValueAsString(dto);
	}
	
}
