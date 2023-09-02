package com.desafioFlow.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafioFlow.estacionamento.modelo.MovimentacaoDiaria;
import com.desafioFlow.estacionamento.modelo.Veiculo;

public interface MovimentacaoDiariaRepository extends JpaRepository<MovimentacaoDiaria, Long> {

	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END "
			+ "FROM MovimentacaoDiaria m WHERE m.veiculo.id = :veiculoId")
	boolean existsByVeiculoId(Long veiculoId);

	@Query("SELECT m FROM MovimentacaoDiaria m WHERE m.veiculo = :veiculo AND m.pago = true")
	MovimentacaoDiaria findByVeiculoAndPagoFalse(Veiculo veiculo);

}
