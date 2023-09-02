package com.desafioFlow.estacionamento.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.desafioFlow.estacionamento.modelo.Veiculo;

public interface VeiculoRepository extends JpaRepository <Veiculo, Long>{
	
	Optional<Veiculo> findByPlaca(String placa);

}
