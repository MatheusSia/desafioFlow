package com.desafioFlow.estacionamento.controller.form;

import com.desafioFlow.estacionamento.modelo.TipoVeiculo;

public class VeiculoForm {

	private String placa;

	private TipoVeiculo tipo;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

}
