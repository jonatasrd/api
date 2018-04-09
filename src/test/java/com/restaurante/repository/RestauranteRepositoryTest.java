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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.restaurante.modelo.Endereco;
import com.restaurante.modelo.Prato;
import com.restaurante.modelo.Restaurante;

@SpringBootTest
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
	private Endereco endereco = new Endereco();
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
    	
    	endereco.setCep(12978000L);
    	endereco.setEstado("Sao Paulo");
    	endereco.setMunicipio("Sao Paulo");
    	endereco.setBairro("Bela Vista");
    	endereco.setLogradouro("Alameda Santos");
    	endereco.setNumero(890L);
    	
    	restaurante.setEndereco(endereco);
    	restaurante.addPrato(prato1);
    	restaurante.addPrato(prato2);
    
    	entityManager.persist(prato1);
    	entityManager.persist(prato2);
    	entityManager.persist(restaurante);
    	entityManager.flush();
    	
    }
    
    @Test
    public void deveRetornarRestaurantePorNome() {
    	restaurantes = restauranteRepository.findByNomeContainingIgnoreCase(restaurante.getNome());
    	
    	assertThat(restaurantes.get(0).getNome()).isEqualTo(restaurante.getNome());
    	assertThat(restaurantes.get(0).getTelefone()).isEqualTo(restaurante.getTelefone());
    	
    	assertThat(restaurantes.get(0).getEndereco().getCep()).isEqualTo(restaurante.getEndereco().getCep());
    	assertThat(restaurantes.get(0).getEndereco().getEstado()).isEqualTo(restaurante.getEndereco().getEstado());
    	assertThat(restaurantes.get(0).getEndereco().getMunicipio()).isEqualTo(restaurante.getEndereco().getMunicipio());
    	assertThat(restaurantes.get(0).getEndereco().getBairro()).isEqualTo(restaurante.getEndereco().getBairro());
    	assertThat(restaurantes.get(0).getEndereco().getLogradouro()).isEqualTo(restaurante.getEndereco().getLogradouro());
    	assertThat(restaurantes.get(0).getEndereco().getNumero()).isEqualTo(restaurante.getEndereco().getNumero());
    	
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
