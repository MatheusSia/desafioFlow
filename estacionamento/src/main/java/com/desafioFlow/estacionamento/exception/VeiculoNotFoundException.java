package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class VeiculoNotFoundException extends RuntimeException {
	public VeiculoNotFoundException(String message) {
		super(message);
	}
}