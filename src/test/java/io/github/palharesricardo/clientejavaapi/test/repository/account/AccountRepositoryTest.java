package io.github.palharesricardo.clientejavaapi.test.repository.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.repository.account.AccountRepository;

/**
 * Class that implements tests of the AccountRepositoryTest functionalities
 * 
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AccountRepositoryTest {
	
	@Autowired
	private AccountRepository repository;
	
	static final String ACCOUNT_NUMBER = "9865320";
	
	private List<Account> accounts;
	
	/**
	 * Method to setup an Account to use in the tests.
	 * 
	 */
	@BeforeAll
	private void setUp() {
		
		Account account = new Account(null, ACCOUNT_NUMBER, 
				AccountTypeEnum.BASIC);
		repository.save(account);
		
		accounts = new ArrayList<>();
		accounts.add(account);
	}
	
	/**
	 * Method that test the repository that save an User in the API.
	 * 
	 */
	@Test
	public void testSave() {
		
		Account account = new Account(null, "98756401", 
				AccountTypeEnum.PREMIUM);
		
		Account response = repository.save(account);
		accounts.add(response);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an Account by the account number.
	 * 
	 */
	@Test
	public void testFindByAccountNumber(){
		Optional<Account> response = repository.findByAccountNumber(ACCOUNT_NUMBER);
		
		assertTrue(response.isPresent());
		assertEquals(ACCOUNT_NUMBER, response.get().getAccountNumber());
	}
	
	/**
	 * Method to remove all Account test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll(accounts);
	}

}
