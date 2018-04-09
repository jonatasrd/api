package com.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.modelo.Prato;
import com.restaurante.service.PratoService;

@RestController
@RequestMapping(path = "/restaurante")
public class PratoController {
	
	@Autowired
	PratoService pratoService;

	@PostMapping("/{id}/prato")
	@ResponseStatus(value= HttpStatus.CREATED)
	public Prato cadastrarEAssociarRestaurante(@PathVariable Long id, Prato prato) {
		return pratoService.cadastrarEAssociarRestaurante(id, prato);
	}

	@PostMapping("/prato")
	@ResponseStatus(value= HttpStatus.CREATED)
	public Prato cadastrar(Prato prato) {
		return pratoService.cadastrar(prato);
	}

	@GetMapping("/prato/{id}")
	public Prato pesquisarPorId(@PathVariable Long id) {
		return pratoService.getPorId(id);
	}

	@GetMapping("/prato/all")
	public List<Prato> getPratos() {
		return pratoService.getPratos();
	}

	@GetMapping("/prato")
	public List<Prato> getPratosPorNome(@RequestParam String nome) {
		return pratoService.getPratosPorNome(nome);
	}

	@DeleteMapping("/prato/{id}")
	@ResponseStatus(value= HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		pratoService.deletar(id);
	}

}
