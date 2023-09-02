package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class VeiculoJaRegistradoException extends RuntimeException {
	public VeiculoJaRegistradoException(String message) {
		super(message);
	}
}