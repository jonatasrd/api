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

import com.restaurante.modelo.Endereco;
import com.restaurante.modelo.Restaurante;
import com.restaurante.service.RestauranteService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class RestauranteControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RestauranteService service;

	private Restaurante restaurante;
	private Endereco endereco = new Endereco();

	private List<Restaurante> restaurantes;

	@Before
	public void setup() {
		restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("Old House");
		restaurante.setTelefone("456788888");

		endereco.setCep(12960000L);
		endereco.setEstado("Sao Paulo");
		endereco.setMunicipio("Sao Paulo");
		endereco.setBairro("Bela Vista");
		endereco.setLogradouro("Alameda Santos");
		endereco.setNumero(890L);
		
		restaurante.setEndereco(endereco);
		
		restaurantes = Arrays.asList(restaurante);
	}
	
	@Test
	public void deveRetornarTodosOsRestaurantes() throws Exception {

		given(service.getRestaurantes()).willReturn(restaurantes);

		mvc.perform(get("/restaurante/all")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome", is(restaurante.getNome())));	
	}
	
	@Test
	public void deveRetornarRestaurantePorId() throws Exception {

		given(service.getPorId(1L)).willReturn(restaurante);

		mvc.perform(get("/restaurante/1")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is(restaurante.getNome())));	
	}
	
	@Test
	public void deveRetornarRestaurantePorNome() throws Exception {

		given(service.getRestaurantesPorNome("Old")).willReturn(restaurantes);

		mvc.perform(get("/restaurante")
				.param("nome", "Old")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome", is(restaurante.getNome())));	
	}
	
	@Test
	public void naoDeveRetornarRestaurantePorNome() throws Exception {

		given(service.getRestaurantesPorNome("Old")).willReturn(restaurantes);

		mvc.perform(get("/restaurante")
				.param("nome", "XXX")
				.with(httpBasic("user","user")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));	
	}
	
	@Test
	public void deveSavarSalvarRestauranteComUserAdmin() throws Exception {

		given(service.cadastrar(restaurante)).willReturn(restaurante);

		mvc.perform(post("/admin/restaurante")
				.with(httpBasic("admin","admin")))
				.andExpect(status().isOk());	
	}
	
	@Test
	public void naoDeveSalvarRestauranteComUserUser() throws Exception {

		given(service.cadastrar(restaurante)).willReturn(restaurante);

		mvc.perform(post("/admin/restaurante")
				.with(httpBasic("user","user")))
				.andExpect(status().isForbidden());	
	}
	
	@Test
	public void deveApagarUmRestauranteComUserAdmin() throws Exception {

		mvc.perform(delete("/admin/restaurante/1")
				.with(httpBasic("admin","admin")))
				.andExpect(status().isOk());	
	}
	
	@Test
	public void naoDeveApagarUmRestauranteUserUser() throws Exception {

		mvc.perform(delete("/admin/restaurante/1")
				.with(httpBasic("user","user")))
				.andExpect(status().isForbidden());	
	}

}
