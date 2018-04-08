package com.restaurante.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.restaurante.modelo.Restaurante;
import com.restaurante.repository.RestauranteRepository;
import com.restaurante.service.RestauranteService;

@RequestScope
@Service
public class RestauranteServiceImpl implements RestauranteService{

	@Autowired
	RestauranteRepository restaurantes;
	
	@Override
	public Restaurante cadastrar(Restaurante restaurante) {
		return restaurantes.save(restaurante);
	}

	@Override
	public void deletar(Long id) {
		restaurantes.deleteById(id);;
	}

	@Override
	public Restaurante getPorId(Long id) {
		return restaurantes.findById(id).get();
	}

	@Override
	public List<Restaurante> getRestaurantesPorNome(String nome) {
		return restaurantes.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public List<Restaurante> getRestaurantes() {
		return restaurantes.findAll();
	}

}
