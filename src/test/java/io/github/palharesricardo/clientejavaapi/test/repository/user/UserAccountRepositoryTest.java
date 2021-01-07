package io.github.palharesricardo.clientejavaapi.test.repository.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.palharesricardo.clientejavaapi.enumeration.AccountTypeEnum;
import io.github.palharesricardo.clientejavaapi.enumeration.RoleEnum;
import io.github.palharesricardo.clientejavaapi.model.account.Account;
import io.github.palharesricardo.clientejavaapi.model.user.User;
import io.github.palharesricardo.clientejavaapi.model.user.UserAccount;
import io.github.palharesricardo.clientejavaapi.repository.account.AccountRepository;
import io.github.palharesricardo.clientejavaapi.repository.user.UserAccountRepository;
import io.github.palharesricardo.clientejavaapi.repository.user.UserRepository;

/**
 * Class that implements tests of the UserAccountRepository functionalities
 * 
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class UserAccountRepositoryTest {
	
	static final String EMAIL = "main@test.com";
	static final String ACCOUNT_NUMBER = "999887";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserAccountRepository userAccRepository;
	
	/**
	 * Method that test the repository that save an UserAccount in the API.
	 */
	@Test
	public void testSave() {
		
		User user = new User(null, "Main User", "9999", EMAIL, 
				RoleEnum.ROLE_ADMIN);
		userRepository.save(user);
		
		Account account = new Account(null, ACCOUNT_NUMBER, 
				AccountTypeEnum.PREMIUM);
		accountRepository.save(account);
		
		UserAccount userAccount = new UserAccount(null, user, account);
		userAccRepository.save(userAccount);
	}
	
	/**
	 * Method to remove all UserAccount test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		userAccRepository.deleteAll();
		accountRepository.deleteAll();
		userRepository.deleteAll();
	}

}
