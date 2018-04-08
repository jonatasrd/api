package com.restaurante.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.restaurante.modelo.Prato;
import com.restaurante.modelo.Restaurante;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RestauranteRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
	@Autowired
    private RestauranteRepository restauranteRepository;
	
	@Autowired
    private PratoRepository pratoRepository;
    
	private Restaurante restaurante;
	private List<Restaurante> restaurantes;
	
	private List<Prato> pratos = new ArrayList<>();

	private Prato prato1;
	private Prato prato2;
    
    @Before
    public void setup() {
    	
    	prato1 = new Prato();
    	prato1.setNome("Lasanha");
    	prato1.setValor(25.0);
    	pratos.add(prato1);
    	
    	prato2 = new Prato();
    	prato2.setNome("Arroz");
    	prato2.setValor(10.0);
    	pratos.add(prato2);
    	
    	restaurante = new Restaurante();
    	restaurante.setNome("Old House");
    	restaurante.setTelefone("456788888");
    	
    	restaurante.setCep(12978000L);
    	restaurante.setEstado("Sao Paulo");
    	restaurante.setMunicipio("Sao Paulo");
    	restaurante.setBairro("Bela Vista");
    	restaurante.setLogradouro("Alameda Santos");
    	restaurante.setNumero(890L);
    	
    	restaurante.addPrato(prato1);
    	restaurante.addPrato(prato2);
    
    	entityManager.persist(prato1);
    	entityManager.persist(prato2);
    	entityManager.persist(restaurante);
    	entityManager.flush();
    	
    	//restaurantes = restauranteRepository.findByNomeContainingIgnoreCase(restaurante.getNome());
    }
    
    @Test
    public void deveRetornarRestaurantePorNome() {
    	restaurantes = restauranteRepository.findByNomeContainingIgnoreCase(restaurante.getNome());
    	
    	assertThat(restaurantes.get(0).getNome()).isEqualTo(restaurante.getNome());
    	assertThat(restaurantes.get(0).getTelefone()).isEqualTo(restaurante.getTelefone());
    	
    	assertThat(restaurantes.get(0).getCep()).isEqualTo(restaurante.getCep());
    	assertThat(restaurantes.get(0).getEstado()).isEqualTo(restaurante.getEstado());
    	assertThat(restaurantes.get(0).getMunicipio()).isEqualTo(restaurante.getMunicipio());
    	assertThat(restaurantes.get(0).getBairro()).isEqualTo(restaurante.getBairro());
    	assertThat(restaurantes.get(0).getLogradouro()).isEqualTo(restaurante.getLogradouro());
    	assertThat(restaurantes.get(0).getNumero()).isEqualTo(restaurante.getNumero());
    	
    	assertThat(restaurantes.get(0).getPratos().get(0).getNome()).isEqualTo(prato1.getNome());
    	
    }
    
    @Test
    public void deveApagarUmRestauranteESeusPratos() {
    	List<Prato> pratos = pratoRepository.findAll();
    	assertThat(pratos.get(0).getNome()).isEqualTo(prato1.getNome());
    	restauranteRepository.deleteAll();
    	pratos = pratoRepository.findAll();
    	assertThat(pratos.size()).isEqualTo(0);
    	
    }
}
