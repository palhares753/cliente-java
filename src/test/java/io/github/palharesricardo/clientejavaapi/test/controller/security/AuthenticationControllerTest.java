package io.github.palharesricardo.clientejavaapi.test.controller.security;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.palharesricardo.clientejavaapi.dto.model.security.JwtUserDTO;
import io.github.palharesricardo.clientejavaapi.enumeration.RoleEnum;
import io.github.palharesricardo.clientejavaapi.model.security.JwtUser;
import io.github.palharesricardo.clientejavaapi.model.security.JwtUserFactory;
import io.github.palharesricardo.clientejavaapi.model.user.User;
import io.github.palharesricardo.clientejavaapi.util.security.BcryptUtil;

/**
 * Class that implements tests of the AuthenticationController features
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
public class AuthenticationControllerTest {

	static final String URL = "/api-travels/v1/auth";
	static final String EMAIL = "admin@company.com";
	static final String PASSWORD = "123456";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserDetailsService userDetailsService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to generate a JWT Token in the API
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGenerateToken() throws Exception {
		
		BDDMockito.given(userDetailsService.loadUserByUsername(Mockito.any(String.class)))
			.willReturn(getMockJwtUser());
		
		 mockMvc.perform(MockMvcRequestBuilders.post(URL)
			 	.content(getJsonPayload(EMAIL, PASSWORD))
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.accept(MediaType.APPLICATION_JSON)
				.headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
	}
	
	/**
	 * Method that fill a mock TokenDTO to use as return in the tests.
	 * 
	 * @author Ricardo Palhares
	 * @since 04/01/2021
	 * 
	 * @param token
	 * @return <code>String</code> with the TokenDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(String email, String password) throws JsonProcessingException {
		JwtUserDTO dto = new JwtUserDTO(email, password);
		return new ObjectMapper().writeValueAsString(dto);
	}
	
	private JwtUser getMockJwtUser() throws ParseException {
		User user = new User(null, "Admin", BcryptUtil.getHash(PASSWORD), EMAIL, RoleEnum.ROLE_ADMIN);
		return JwtUserFactory.create(user);
	}
	
}
