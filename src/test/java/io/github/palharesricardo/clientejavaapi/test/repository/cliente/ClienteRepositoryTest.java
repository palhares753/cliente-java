package io.github.palharesricardo.clientejavaapi.test.repository.Cliente;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.enumeration.ClienteTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.model.Cliente.Cliente;
import io.github.palharesricardo.clientejavaapi.repository.account.AccountRepository;
import io.github.palharesricardo.clientejavaapi.repository.Cliente.ClienteRepository;

/**
 * Class that implements tests of the ClienteRepository features
 * 
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	static final String CPF = "zzzzzzzzzzzz";
	
	Account account;
	
	/**
	 * Method to setup an object Cliente to use in the tests.
	 * 
	 */
	@BeforeAll
	private void setUp() {
		
		account = new Account(null, CPF, AccountTypeEnum.BASIC);
		accountRepository.save(account);
		
		Cliente cliente = new Cliente(null, "xxxxxxxxxx", 'Ricardo',  'Rua sei la');
		
		repository.save(Cliente);
	}
	
	/**
	 * Method that test the repository that save an object Cliente in the API.
	 * 
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		Cliente cliente = new Cliente(null, "xxxxxxxxxx", 'Ricardo',  'Rua sei la');
		
		Cliente response = repository.save(Cliente);
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an object Cliente by the orderNumber.
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByOrderNumber(){
		
		Optional<Cliente> response = repository.findByOrderNumber("220788");
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method to remove all Clientes test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
		accountRepository.delete(account);
	}

}
