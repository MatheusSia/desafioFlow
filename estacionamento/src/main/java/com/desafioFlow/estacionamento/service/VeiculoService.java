package com.desafioFlow.estacionamento.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafioFlow.estacionamento.exception.PlacaInvalidaException;
import com.desafioFlow.estacionamento.exception.TipoVeiculoInvalidoException;
import com.desafioFlow.estacionamento.exception.VeiculoNotFoundException;
import com.desafioFlow.estacionamento.modelo.TipoVeiculo;
import com.desafioFlow.estacionamento.modelo.Veiculo;
import com.desafioFlow.estacionamento.repository.VeiculoRepository;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;

	public Veiculo registrarEntrada(String placa, TipoVeiculo tipo) {
		validarPlaca(placa);
		validarTipo(tipo);

		if (placaJaExiste(placa)) {
			throw new PlacaInvalidaException("Placa já existe no banco de dados");
		}

		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca(placa.toUpperCase()); // Transforma a placa em letras maiúsculas
		veiculo.setTipo(tipo);
		LocalDateTime horarioEntrada = LocalDateTime.now();
		veiculo.setHorarioEntrada(horarioEntrada);
		return veiculoRepository.save(veiculo);
	}

	private void validarPlaca(String placa) {
		if (!placa.matches("^[A-Z0-9]+$")) {
			throw new PlacaInvalidaException(
					"Placa inserida incorretamente - Insira a placa apenas com letras maiúsculas e números");
		}
	}

	private void validarTipo(TipoVeiculo tipo) {
		if (tipo != TipoVeiculo.CARRO && tipo != TipoVeiculo.MOTO) {
			throw new TipoVeiculoInvalidoException(
					"Tipo de veículo inserido incorretamente - Insira apenas CARRO ou MOTO");
		}
	}

	private boolean placaJaExiste(String placa) {
		return veiculoRepository.findByPlaca(placa).isPresent();
	}

	public Veiculo obterPorPlaca(String placa) {
		return veiculoRepository.findByPlaca(placa)
				.orElseThrow(() -> new VeiculoNotFoundException("Veículo não encontrado com a placa: " + placa));
	}
	
	public List<Veiculo> listarTodosVeiculos() {
        return veiculoRepository.findAll();
    }

}
