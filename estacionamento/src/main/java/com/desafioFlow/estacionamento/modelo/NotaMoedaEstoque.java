package com.desafioFlow.estacionamento.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nota_moeda_estoque")
public class NotaMoedaEstoque {

	@Id
	@Column(name = "nota_moeda_estoque_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "valor")
	private BigDecimal valor;

	@Column(nullable = false, name = "quantidade")
	private int quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public NotaMoedaEstoque(BigDecimal valor, int quantidade) {
		this.valor = valor;
		this.quantidade = quantidade;
	}

	// Construtor vazio para JPA
	public NotaMoedaEstoque() {
	}

	// Métodos auxiliares
	public void adicionarQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

}