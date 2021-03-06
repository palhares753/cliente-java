package io.github.palharesricardo.clientejavaapi.test.service.account;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.repository.account.AccountRepository;
import io.github.palharesricardo.clientejavaapi.service.account.AccountService;

/**
 * Class that implements tests of the AccountService features.
 * 
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class AccountServiceTest {
	
	@Autowired
	private AccountService service;

	@MockBean
	private AccountRepository repository;
	
	static final String ACCOUNT_NUMBER = "123456";
	
	/**
	 * Method to test the creation of an Account.
	 * 
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Account.class)))
			.willReturn(getMockAccount());
		Account response = service.save(new Account());
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the service that search for an Account by the account
	 * number.
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByAccountNumber(){
		
		BDDMockito.given(repository.findByAccountNumber(Mockito.anyString()))
			.willReturn(Optional.ofNullable(new Account()));

		Optional<Account> response = service.findByAccountNumber(ACCOUNT_NUMBER);
		assertTrue(!response.isEmpty());
	}
	
	/**
	 * Method to remove all Account test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	
	/**
	 * Method that fill a mock of a Account to use as return in the tests.
	 * 
	 * 
	 * @return <code>Account</code> object
	 */
	private Account getMockAccount() {
		return new Account(1L, ACCOUNT_NUMBER, AccountTypeEnum.BASIC);
	}

}
