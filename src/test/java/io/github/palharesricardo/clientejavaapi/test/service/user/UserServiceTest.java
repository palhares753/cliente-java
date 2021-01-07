package io.github.palharesricardo.clientejavaapi.test.service.user;

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

import io.github.palharesricardo.clientejavaapi.enumeration.RoleEnum;
import io.github.palharesricardo.clientejavaapi.model.user.User;
import io.github.palharesricardo.clientejavaapi.repository.user.UserRepository;
import io.github.palharesricardo.clientejavaapi.service.user.UserService;

/**
 * Class that implements tests of the UserService features.
 * 
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class UserServiceTest {
	
	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repository;
	
	static final String EMAIL = "email@test.com";
	
	/**
	 * Method to test the creation of an User.
	 * 
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(User.class)))
			.willReturn(getMockUser());
		User response = service.save(new User());
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the service that search for an User by the email.
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByEmail(){
		
		BDDMockito.given(repository.findByEmail(Mockito.anyString()))
			.willReturn(Optional.ofNullable(new User()));

		Optional<User> response = service.findByEmail(EMAIL);
		assertTrue(!response.isEmpty());
	}
	
	/**
	 * Method that fill a mock of a User to use as return in the tests.
	 * 
	 * 
	 * @return <code>User</code> object
	 */
	private User getMockUser() {
		return new User(1L, "Test User", "123", EMAIL, RoleEnum.ROLE_ADMIN);
	}
	
	/**
	 * Method to remove all User test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
