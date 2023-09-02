package com.desafioFlow.estacionamento.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafioFlow.estacionamento.controller.form.NotaMoedaEstoqueForm;
import com.desafioFlow.estacionamento.exception.PlacaInvalidaException;
import com.desafioFlow.estacionamento.exception.TipoVeiculoInvalidoException;
import com.desafioFlow.estacionamento.exception.TrocoInsuficienteException;
import com.desafioFlow.estacionamento.exception.ValorInseridoMenorException;
import com.desafioFlow.estacionamento.exception.VeiculoNotFoundException;
import com.desafioFlow.estacionamento.modelo.MovimentacaoDiaria;
import com.desafioFlow.estacionamento.modelo.Veiculo;
import com.desafioFlow.estacionamento.service.MovimentacaoDiariaService;
import com.desafioFlow.estacionamento.service.VeiculoService;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoDiariaController {

	@Autowired
	private MovimentacaoDiariaService movimentacaoDiariaService;

	@Autowired
	private VeiculoService veiculoService;

	@GetMapping
	public ResponseEntity<List<MovimentacaoDiaria>> listarMovimentacoes() {
		List<MovimentacaoDiaria> movimentacoes = movimentacaoDiariaService.listarTodasMovimentacoes();
		return ResponseEntity.ok(movimentacoes);
	}

	@GetMapping("/{placa}/calcular-valor-saida")
	public ResponseEntity<String> calcularValorSaida(@PathVariable String placa) {
		try {
			Veiculo veiculo = veiculoService.obterPorPlaca(placa);

			LocalDateTime entrada = veiculo.getHorarioEntrada();
			LocalDateTime saida = LocalDateTime.now();

			BigDecimal valorTotal = movimentacaoDiariaService.calcularValorSaida(veiculo, entrada, saida);

			return ResponseEntity.ok("Valor a pagar: R$" + valorTotal);
		} catch (VeiculoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{placa}/pagamento")
	public ResponseEntity<String> realizarPagamento(@PathVariable String placa,
			@RequestBody List<NotaMoedaEstoqueForm> notasMoedasInseridas) {
		try {
			Veiculo veiculo = veiculoService.obterPorPlaca(placa);

			MovimentacaoDiaria movimentacao = movimentacaoDiariaService.obterMovimentacaoNaoPagaPorVeiculo(veiculo);
			if (movimentacao != null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veículo com saída já liberada.");
			}

			movimentacao = movimentacaoDiariaService.calcularMovimentacaoSaida(veiculo); // Calcular valor sem salvar
			movimentacaoDiariaService.pagarServico(movimentacao, notasMoedasInseridas);

			return ResponseEntity.ok("Pagamento efetuado com sucesso - Saída liberada.");
		} catch (PlacaInvalidaException | TipoVeiculoInvalidoException | ValorInseridoMenorException
				| TrocoInsuficienteException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
