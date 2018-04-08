package com.restaurante.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.restaurante.modelo.Restaurante;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RestauranteRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
	@Autowired
    private RestauranteRepository restauranteRepository;
    
	private Restaurante restaurante;
	private List<Restaurante> restaurantes;
    
    @Before
    public void setup() {
    	restaurante = new Restaurante();
    	restaurante.setNome("Old House");
    	restaurante.setTelefone("456788888");
    	
    	restaurante.setCep(12978000L);
    	restaurante.setEstado("Sao Paulo");
    	restaurante.setMunicipio("Sao Paulo");
    	restaurante.setBairro("Bela Vista");
    	restaurante.setLogradouro("Alameda Santos");
    	restaurante.setNumero(890L);
    
    	
    	entityManager.persist(restaurante);
    	entityManager.flush();
    	
    	restaurantes = restauranteRepository.findByNomeContainingIgnoreCase(restaurante.getNome());
    }
    
    @Test
    public void deveRetornarRestaurantePorNome() {
    	assertThat(restaurantes.get(0).getNome()).isEqualTo(restaurante.getNome());
    	assertThat(restaurantes.get(0).getTelefone()).isEqualTo(restaurante.getTelefone());
    	
    	assertThat(restaurantes.get(0).getCep()).isEqualTo(restaurante.getCep());
    	assertThat(restaurantes.get(0).getEstado()).isEqualTo(restaurante.getEstado());
    	assertThat(restaurantes.get(0).getMunicipio()).isEqualTo(restaurante.getMunicipio());
    	assertThat(restaurantes.get(0).getBairro()).isEqualTo(restaurante.getBairro());
    	assertThat(restaurantes.get(0).getLogradouro()).isEqualTo(restaurante.getLogradouro());
    	assertThat(restaurantes.get(0).getNumero()).isEqualTo(restaurante.getNumero());
    }
}
