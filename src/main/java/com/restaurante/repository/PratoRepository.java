package com.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.modelo.Prato;

public interface PratoRepository extends JpaRepository<Prato, Long> {

	List<Prato> findByNomeContainingIgnoreCase(String Nome);
}
