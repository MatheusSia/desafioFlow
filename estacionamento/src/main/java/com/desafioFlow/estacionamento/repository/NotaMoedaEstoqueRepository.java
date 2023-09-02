package com.desafioFlow.estacionamento.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafioFlow.estacionamento.modelo.NotaMoedaEstoque;

public interface NotaMoedaEstoqueRepository extends JpaRepository<NotaMoedaEstoque, Long> {

	Optional<NotaMoedaEstoque> findByValor(BigDecimal valor);

}
