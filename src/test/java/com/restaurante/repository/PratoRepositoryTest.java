package com.restaurante.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.restaurante.modelo.Prato;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class PratoRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PratoRepository pratoRepository;
    
	private List<Prato> pratos;
	private Prato prato;
    
    @Before
    public void setup() {
    	prato = new Prato();
    	prato.setNome("Lasanha");
    	prato.setValor(25.0);
    	
    	entityManager.persist(prato);
    	entityManager.flush();
    	
    	pratos = pratoRepository.findByNomeContainingIgnoreCase(prato.getNome());
    }
	
    @Test
    public void deveRetornarPratoPorNome() {
    	assertThat(pratos.get(0).getNome()).isEqualTo(prato.getNome());
    	assertThat(pratos.get(0).getValor()).isEqualTo(prato.getValor());
    }
}
