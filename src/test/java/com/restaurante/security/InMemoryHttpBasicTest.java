package com.restaurante.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class InMemoryHttpBasicTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void acessoProtegido() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(unauthenticated())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void loginUsuarioAdmin() throws Exception {
		this.mockMvc
				.perform(get("/").with(httpBasic("admin", "admin")))
				.andExpect(status().isOk());
	}
	
	@Test
	public void loginUsuarioUser() throws Exception {
		this.mockMvc
				.perform(get("/").with(httpBasic("user", "user")))
				.andExpect(status().isOk());
	}

	@Test
	public void loginInvalidUser() throws Exception {
		this.mockMvc.perform(formLogin().user("invalid").password("invalid"))
				.andExpect(unauthenticated());
	}
}
