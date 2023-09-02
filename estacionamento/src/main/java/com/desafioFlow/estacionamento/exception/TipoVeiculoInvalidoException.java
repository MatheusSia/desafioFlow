package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class TipoVeiculoInvalidoException extends RuntimeException {
	public TipoVeiculoInvalidoException(String message) {
		super(message);
	}
}
