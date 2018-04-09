package com.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.modelo.Restaurante;
import com.restaurante.service.RestauranteService;

@RestController
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;

	@PostMapping("/admin/restaurante")
	@ResponseStatus(value= HttpStatus.CREATED)
	public Restaurante cadastrar(Restaurante restaurante) {
		return restauranteService.cadastrar(restaurante);
	}

	@DeleteMapping("/admin/restaurante/{id}")
	@ResponseStatus(value= HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		restauranteService.deletar(id);
	}
	
	@GetMapping("/restaurante/{id}")
	public Restaurante pesquisarPorId(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.getPorId(id);
		return restaurante;
	}

	@GetMapping("/restaurante")
	public List<Restaurante> getRestaurantesPorNome(@RequestParam String nome) {
		return restauranteService.getRestaurantesPorNome(nome);
	}

	@GetMapping("/restaurante/all")
	public List<Restaurante> getRestaurantes() {
		return restauranteService.getRestaurantes();
	}


}
