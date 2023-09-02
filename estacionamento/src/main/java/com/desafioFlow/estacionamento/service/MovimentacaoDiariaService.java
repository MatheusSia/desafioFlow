package com.desafioFlow.estacionamento.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafioFlow.estacionamento.controller.form.NotaMoedaEstoqueForm;
import com.desafioFlow.estacionamento.exception.TrocoInsuficienteException;
import com.desafioFlow.estacionamento.exception.ValorInseridoMenorException;
import com.desafioFlow.estacionamento.modelo.MovimentacaoDiaria;
import com.desafioFlow.estacionamento.modelo.NotaMoedaEstoque;
import com.desafioFlow.estacionamento.modelo.TipoVeiculo;
import com.desafioFlow.estacionamento.modelo.Veiculo;
import com.desafioFlow.estacionamento.repository.MovimentacaoDiariaRepository;
import com.desafioFlow.estacionamento.repository.NotaMoedaEstoqueRepository;

@Service
public class MovimentacaoDiariaService {

	@Autowired
	private MovimentacaoDiariaRepository movimentacaoDiariaRepository;

	@Autowired
	private NotaMoedaEstoqueService estoqueService;

	@Autowired
	private NotaMoedaEstoqueRepository notaMoedaEstoqueRepository;

	public MovimentacaoDiaria calcularMovimentacaoSaida(Veiculo veiculo) {
		MovimentacaoDiaria movimentacao = new MovimentacaoDiaria();
		movimentacao.setVeiculo(veiculo);
		movimentacao.setEntrada(veiculo.getHorarioEntrada());

		LocalDateTime saida = LocalDateTime.now();
		movimentacao.setSaida(saida);

		BigDecimal valorTotal = calcularValorSaida(veiculo, movimentacao.getEntrada(), saida);
		movimentacao.setValorTotal(valorTotal);

		return movimentacao;
	}

	public BigDecimal calcularValorSaida(Veiculo veiculo, LocalDateTime entrada, LocalDateTime saida) {
		Duration duracao = Duration.between(entrada, saida);
		long duracaoMinutos = duracao.toMinutes();
		long duracaoHoras;
		if (duracaoMinutos % 60 > 20) {
			duracaoHoras = duracaoMinutos / 60 + 1;
		} else {
			duracaoHoras = duracaoMinutos / 60;
		}
		BigDecimal valorTotal;
		if (veiculo.getTipo() == TipoVeiculo.CARRO) {
			valorTotal = BigDecimal.valueOf(duracaoHoras * 10); // R$10 por hora
		} else {
			valorTotal = BigDecimal.valueOf(duracaoHoras * 7); // R$7 por hora
		}
		return valorTotal;
	}

	public void pagarServico(MovimentacaoDiaria movimentacao, List<NotaMoedaEstoqueForm> notasMoedasInseridas) {
		BigDecimal valorTotalServico = movimentacao.getValorTotal();
		BigDecimal valorTotalInserido = calcularValorTotalInserido(notasMoedasInseridas);

		if (valorTotalInserido.compareTo(valorTotalServico) == 0) {
			atualizarEstoqueETransferirTroco(notasMoedasInseridas, BigDecimal.ZERO);
		} else if (valorTotalInserido.compareTo(valorTotalServico) < 0) {
			throw new ValorInseridoMenorException("Valor inserido menor que o esperado - Esperando um valor maior.");
		} else {
			BigDecimal troco = valorTotalInserido.subtract(valorTotalServico);
			atualizarEstoqueETransferirTroco(notasMoedasInseridas, troco);
		}

		movimentacao.setPago(true);
		movimentacaoDiariaRepository.save(movimentacao);
	}

	private void atualizarEstoqueETransferirTroco(List<NotaMoedaEstoqueForm> notasMoedasInseridas, BigDecimal troco) {
		List<NotaMoedaEstoqueForm> notasMoedasTroco = calcularTroco(troco);
		List<NotaMoedaEstoqueForm> itensEstoqueAtualizados = new ArrayList<>();

		itensEstoqueAtualizados.addAll(notasMoedasInseridas);

		for (NotaMoedaEstoqueForm notaMoedaTroco : notasMoedasTroco) {
			Optional<NotaMoedaEstoque> optionalNotaMoedaEstoque = notaMoedaEstoqueRepository
					.findByValor(notaMoedaTroco.getValor());

			if (optionalNotaMoedaEstoque.isPresent()) {
				NotaMoedaEstoque notaMoedaEstoque = optionalNotaMoedaEstoque.get();
				int novaQuantidade = notaMoedaEstoque.getQuantidade() - notaMoedaTroco.getQuantidade();
				notaMoedaEstoque.setQuantidade(novaQuantidade);
				notaMoedaEstoqueRepository.save(notaMoedaEstoque);
			} else {
				throw new RuntimeException("Nota/moeda não encontrada: " + notaMoedaTroco.getValor());
			}
		}

		estoqueService.atualizarEstoqueComPagamento(itensEstoqueAtualizados);
	}

	public List<NotaMoedaEstoqueForm> calcularTroco(BigDecimal troco) {
		List<NotaMoedaEstoqueForm> notasMoedasTroco = new ArrayList<>();
		List<NotaMoedaEstoque> notasMoedasDisponiveis = notaMoedaEstoqueRepository.findAll();

		notasMoedasDisponiveis.sort(Comparator.comparing(NotaMoedaEstoque::getValor).reversed());

		for (NotaMoedaEstoque notaMoeda : notasMoedasDisponiveis) {
			BigDecimal valorNota = notaMoeda.getValor();
			int quantidadeDisponivel = notaMoeda.getQuantidade();

			if (valorNota.compareTo(troco) <= 0 && quantidadeDisponivel > 0) {
				int quantidadeNecessaria = troco.divideToIntegralValue(valorNota).intValue();
				int quantidadeUsada = Math.min(quantidadeNecessaria, quantidadeDisponivel);

				if (quantidadeUsada > 0) {
					notasMoedasTroco.add(new NotaMoedaEstoqueForm(valorNota, quantidadeUsada));
					troco = troco.subtract(valorNota.multiply(BigDecimal.valueOf(quantidadeUsada)));
				}
			}
		}

		if (troco.compareTo(BigDecimal.ZERO) > 0) {
			throw new TrocoInsuficienteException("Não é possível fornecer troco com as notas/moedas disponíveis.");
		}

		return notasMoedasTroco;
	}

	private BigDecimal calcularValorTotalInserido(List<NotaMoedaEstoqueForm> notasMoedasInseridas) {
		BigDecimal valorTotalInserido = BigDecimal.ZERO;

		for (NotaMoedaEstoqueForm item : notasMoedasInseridas) {
			BigDecimal valorItem = item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
			valorTotalInserido = valorTotalInserido.add(valorItem);
		}

		return valorTotalInserido;
	}

	public boolean veiculoJaRegistrado(Long veiculoId) {
		return movimentacaoDiariaRepository.existsByVeiculoId(veiculoId);
	}

	public MovimentacaoDiaria obterMovimentacaoNaoPagaPorVeiculo(Veiculo veiculo) {
		return movimentacaoDiariaRepository.findByVeiculoAndPagoFalse(veiculo);
	}

	public List<MovimentacaoDiaria> listarTodasMovimentacoes() {
		return movimentacaoDiariaRepository.findAll();
	}

}
