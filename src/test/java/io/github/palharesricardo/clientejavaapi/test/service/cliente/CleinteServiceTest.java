package io.github.palharesricardo.clientejavaapi.test.service.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.enumeration.ClienteTypeEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.model.Cliente.Cliente;
import io.github.palharesricardo.clientejavaapi.repository.Cliente.ClienteRepository;
import io.github.palharesricardo.clientejavaapi.service.Cliente.ClienteService;

/**
 * Class that implements tests of the ClienteService features.
 * 
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class ClienteServiceTest {
	
	@Autowired
	private ClienteService service;

	@MockBean
	private ClienteRepository repository;
	
	private static final LocalDateTime DATE = LocalDateTime.now();
	
	/**
	 * Method to setup a Cliente to use in the tests.
	 * 
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Cliente.class)))
			.willReturn(getMockCliente());
		Cliente response = service.save(new Cliente());
		
		assertNotNull(response);
		assertEquals("123456", response.getOrderNumber());
	}
	
	/**
	 * Method that test the service that search for an object Cliente by the order number.
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByOrderNumber() {
		
		BDDMockito.given(repository.findByCPF(Mockito.anyString()))
			.willReturn(Optional.of(new Cliente()));
		
		Optional<Cliente> response = service.findByOrderNumber("123456");
		assertTrue(!response.isEmpty());
	}
	
	
	/**
	 * Method to remove all Cliente test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
