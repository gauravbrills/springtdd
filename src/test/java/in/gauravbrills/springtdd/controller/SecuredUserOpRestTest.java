/*
 * SecuredUserOpRestTest.java
 * 
 * Copyright Gaurav Rawat 2014
 *
 * @author Gaurav Rawat
 */
package in.gauravbrills.springtdd.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import in.gauravbrills.springtdd.model.User;
import in.gauravbrills.springtdd.spring.RestConfig;
import in.gauravbrills.springtdd.spring.SecurityConfig;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class UserOpRestTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { RestConfig.class, SecurityConfig.class })
public class SecuredUserOpRestTest {

	/** The wac. */
	@Autowired
	private WebApplicationContext wac;

	/** The spring security filter chain. */
	@Resource
	private Filter springSecurityFilterChain;

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The content type. */
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.addFilters(springSecurityFilterChain).build();
	}

	/**
	 * Test list users.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testListUsersAuthFail() throws Exception {
		this.mockMvc.perform(get("/sec/users")).andExpect(
				status().isUnauthorized());
	}

	/**
	 * Test list users.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testListUsers() throws Exception {
		this.mockMvc
				.perform(get("/sec/users").with(httpBasic("user", "password")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("Kevin")))
				.andExpect(jsonPath("$[0].surname", is("Bernard")));
	}

	/**
	 * Test get by name.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetByNameUsingWith() throws Exception {
		this.mockMvc.perform(
				get("/sec/users/{name}", "John").with(
						user("user").password("password"))).andExpect(
				status().isUnauthorized());
	}

	/**
	 * Test save user.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSaveUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("Mr", "John", "Doe");
		String body = mapper.writeValueAsString(user);
		this.mockMvc.perform(
				post("/sec/users").contentType(contentType).content(body)
						.with(httpBasic("admin", "password"))).andExpect(
				status().isOk());
	}

	@Test
	public void testSaveUserWrngPass() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("Mr", "John", "Doe");
		String body = mapper.writeValueAsString(user);
		this.mockMvc.perform(
				post("/sec/users").contentType(contentType).content(body)
						.with(httpBasic("admin", "a"))).andExpect(
				status().isUnauthorized());
	}

	/**
	 * Test save user_failedvalidation.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSaveUser_failedvalidation() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User(null, "John", "Doe");
		String body = mapper.writeValueAsString(user);
		this.mockMvc.perform(
				post("/sec/users").contentType(contentType).content(body)
						.with(httpBasic("admin", "password"))).andExpect(
				status().isBadRequest());
	}
}
