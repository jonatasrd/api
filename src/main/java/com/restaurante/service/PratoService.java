package com.restaurante.service;

import java.util.List;

import com.restaurante.modelo.Prato;

public interface PratoService {
	Prato cadastrarEAssociarRestaurante(Long id, Prato prato);
	Prato cadastrar(Prato prato);
	Prato getPorId(Long id);
	List<Prato> getPratos();
	List<Prato> getPratosPorNome(String nome);
	void deletar(Long id);
}
