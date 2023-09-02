package com.desafioFlow.estacionamento.controller.form;

import java.math.BigDecimal;

public class NotaMoedaEstoqueForm {

	private BigDecimal valor;

	private int quantidade;

	public NotaMoedaEstoqueForm(BigDecimal valor, int quantidade) {
		this.valor = valor;
		this.quantidade = quantidade;
	}

	// Construtor vazio para JPA
	public NotaMoedaEstoqueForm() {
	}

	// Métodos auxiliares
	public void adicionarQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
