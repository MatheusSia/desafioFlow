package com.desafioFlow.estacionamento.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desafioFlow.estacionamento.controller.form.NotaMoedaEstoqueForm;
import com.desafioFlow.estacionamento.exception.ValorInexistenteException;
import com.desafioFlow.estacionamento.exception.ValorInvalidoException;
import com.desafioFlow.estacionamento.modelo.NotaMoedaEstoque;
import com.desafioFlow.estacionamento.repository.NotaMoedaEstoqueRepository;

@Service
public class NotaMoedaEstoqueService {

	@Autowired
	private NotaMoedaEstoqueRepository notaMoedaEstoqueRepository;

	public List<NotaMoedaEstoque> obterEstoque() {
		return notaMoedaEstoqueRepository.findAll();
	}

	public NotaMoedaEstoque armazenarDinheiro(NotaMoedaEstoqueForm form) {
		validarValoresPermitidos(form);

		Optional<NotaMoedaEstoque> itemExistenteOptional = notaMoedaEstoqueRepository.findByValor(form.getValor());

		if (itemExistenteOptional.isPresent()) {
			NotaMoedaEstoque itemExistente = itemExistenteOptional.get();
			itemExistente.adicionarQuantidade(form.getQuantidade());
			return notaMoedaEstoqueRepository.save(itemExistente);
		} else {
			NotaMoedaEstoque novoItem = new NotaMoedaEstoque(form.getValor(), form.getQuantidade());
			return notaMoedaEstoqueRepository.save(novoItem);
		}
	}

	public BigDecimal calcularValorInserido(List<NotaMoedaEstoqueForm> notasMoedasInseridas) {
		BigDecimal valorTotalInserido = BigDecimal.ZERO;
		for (NotaMoedaEstoqueForm item : notasMoedasInseridas) {
			valorTotalInserido = valorTotalInserido
					.add(item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())));
		}
		return valorTotalInserido;
	}

	public void validarValoresPermitidos(NotaMoedaEstoqueForm estoqueItem) { // encapsular o codigo
		BigDecimal valorItem = estoqueItem.getValor();
		if (!(valorItem.equals(new BigDecimal("100")) || valorItem.equals(new BigDecimal("50"))
				|| valorItem.equals(new BigDecimal("20")) || valorItem.equals(new BigDecimal("10"))
				|| valorItem.equals(new BigDecimal("5")) || valorItem.equals(new BigDecimal("2"))
				|| valorItem.equals(new BigDecimal("1")) || valorItem.equals(new BigDecimal("0.5"))
				|| valorItem.equals(new BigDecimal("0.25")) || valorItem.equals(new BigDecimal("0.10"))
				|| valorItem.equals(new BigDecimal("0.05")))) {
			throw new ValorInvalidoException("Valor inválido para nota/moeda.");
		}

	}

	public void atualizarEstoqueComPagamento(List<NotaMoedaEstoqueForm> notasMoedasInseridas) {
		for (NotaMoedaEstoqueForm item : notasMoedasInseridas) {
			NotaMoedaEstoque estoqueItem = notaMoedaEstoqueRepository.findByValor(item.getValor())
					.orElseThrow(() -> new ValorInexistenteException("Não existe esse valor no caixa"));

			estoqueItem.adicionarQuantidade(item.getQuantidade());
			notaMoedaEstoqueRepository.save(estoqueItem);
		}
	}

}