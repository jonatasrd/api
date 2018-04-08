package com.restaurante.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.restaurante.modelo.Prato;
import com.restaurante.modelo.Restaurante;
import com.restaurante.repository.PratoRepository;
import com.restaurante.repository.RestauranteRepository;
import com.restaurante.service.PratoService;

@RequestScope
@Service
public class PratoServiceImpl implements PratoService {
	
	@Autowired
	PratoRepository pratos;

	@Autowired
	RestauranteRepository restaurantes;

	@Override
	public Prato cadastrarEAssociarRestaurante(Long id, Prato prato) {
		Optional<Restaurante> restaurante = restaurantes.findById(id);
		prato.setRestaurante(restaurante.get());
		pratos.save(prato);
		return prato;
	}

	@Override
	public Prato cadastrar(Prato prato) {
		return pratos.save(prato);
	}

	@Override
	public Prato getPorId(Long id) {
		Prato prato = pratos.findById(id).get();
		return prato;
	}

	@Override
	public List<Prato> getPratos() {
		return pratos.findAll();
	}

	@Override
	public List<Prato> getPratosPorNome(String nome) {
		return pratos.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public void deletar(Long id) {
		pratos.deleteById(id);
	}

}
