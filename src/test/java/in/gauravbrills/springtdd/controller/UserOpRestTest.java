/*
 * UserOpRestTest.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import in.gauravbrills.springtdd.model.User;
import in.gauravbrills.springtdd.service.UserService;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class UserOpRestTest.
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class UserOpRestTest {

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The user service. */
	@Mock
	private UserService userService;

	/** The login controller. */
	@InjectMocks
	private UserOpController opController;

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		// Initalizes Mockito
		MockitoAnnotations.initMocks(this);
		// Builds Mock Controllers for Spring Mvc
		this.mockMvc = MockMvcBuilders.standaloneSetup(opController)
				.setMessageConverters(getMessageConv()).build();
	}

	private HttpMessageConverter<?> getMessageConv() {
		final ClassLoader classLoader = getClass().getClassLoader();
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = null;
		if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper",
				classLoader)) {
			jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jackson2HttpMessageConverter.getObjectMapper().disable(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			// Json Pretty Formatting
			jackson2HttpMessageConverter.getObjectMapper().enable(
					SerializationFeature.INDENT_OUTPUT);
		}
		return jackson2HttpMessageConverter;
	}

	/**
	 * Test list users.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testListUsers() throws Exception {
		List<User> users = Arrays.asList(new User[] {
				new User("Mr", "John", "Doe"),
				new User("Mr", "Jimm", "Kendrick") });
		Mockito.when(userService.getAllUsers()).thenReturn(users);

		this.mockMvc.perform(get("/users")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].name", is("Jimm")))
				.andExpect(jsonPath("$[0].surname", is("Doe")));
	}

	@Test
	public void testGetByName() throws Exception {
		User user = new User("Mr", "John", "Doe");
		Mockito.when(userService.getByName("John")).thenReturn(user);

		this.mockMvc.perform(get("/users/{name}", "John"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.name", is("John")))
				.andExpect(jsonPath("$.title", is("Mr"))).andDo(print());
	}

	@Test
	public void testSaveUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("Mr", "John", "Doe");
		Mockito.doNothing().when(userService).save(user);
		String body = mapper.writeValueAsString(user);
		this.mockMvc
				.perform(
						post("/users").contentType(contentType)
								.content(body)).andExpect(status().isOk())
				.andDo(print());
	}
}
