package com.restaurante.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.restaurante.modelo.Prato;
import com.restaurante.service.PratoService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class PratoControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	PratoService service;
    
	private Prato prato;
	private List<Prato> pratos;

	@Before
    public void setup() {
    	prato = new Prato();
    	prato.setId(1L);
    	prato.setNome("Lasanha");
    	prato.setValor(25.0);
    	pratos = Arrays.asList(prato);
    }

	@Test
	public void deveRetornarTodosOsPratos() throws Exception {

		given(service.getPratos()).willReturn(pratos);

		mvc.perform(get("/restaurante/prato/all")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome", is(prato.getNome())));	
	}
	
	@Test
	public void DeveRetornarPratoPorId() throws Exception {

		given(service.getPorId(1L)).willReturn(prato);

		mvc.perform(get("/restaurante/prato/1")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is(prato.getNome())));	
	}
	
	@Test
	public void deveRetornarPratoPorNome() throws Exception {

		given(service.getPratosPorNome("Las")).willReturn(pratos);

		mvc.perform(get("/restaurante/prato")
				.param("nome", "Las")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome", is(prato.getNome())));	
	}
	
	@Test
	public void naoDeveRetornarPratoPorNome() throws Exception {

		given(service.getPratosPorNome("Las")).willReturn(pratos);

		mvc.perform(get("/restaurante/prato")
				.param("nome", "XXX")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void deveSavarSalvarPratoComUserUser() throws Exception {

		given(service.cadastrar(prato)).willReturn(prato);

		mvc.perform(post("/restaurante/prato")
				.with(httpBasic("user","user")))
				.andExpect(status().isCreated());	
	}
	
	@Test
	public void deveSavarSalvarPratoComUserAdmin() throws Exception {

		given(service.cadastrar(prato)).willReturn(prato);

		mvc.perform(post("/restaurante/prato")
				.with(httpBasic("admin","admin")))
				.andExpect(status().isCreated());	
	}
	
	@Test
	public void deveApagarUmPratoComUserAdmin() throws Exception {

		mvc.perform(delete("/restaurante/prato/1")
				.with(httpBasic("admin","admin")))
				.andExpect(status().isNoContent());	
	}
	
	@Test
	public void deveApagarUmPratoComUserUser() throws Exception {

		mvc.perform(delete("/restaurante/prato/1")
				.with(httpBasic("user","user")))
				.andExpect(status().isNoContent());	
	}
}
