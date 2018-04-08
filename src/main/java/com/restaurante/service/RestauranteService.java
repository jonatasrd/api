package com.restaurante.service;

import java.util.List;

import com.restaurante.modelo.Restaurante;

public interface RestauranteService {
	Restaurante cadastrar(Restaurante restaurante);
	void deletar(Long id);
	Restaurante getPorId(Long id);
	List<Restaurante> getRestaurantesPorNome(String nome);
	List<Restaurante> getRestaurantes();
}
