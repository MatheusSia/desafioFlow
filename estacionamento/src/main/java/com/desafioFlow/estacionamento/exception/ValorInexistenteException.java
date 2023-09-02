package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class ValorInexistenteException extends RuntimeException {
	public ValorInexistenteException(String message) {
		super(message);
	}
}