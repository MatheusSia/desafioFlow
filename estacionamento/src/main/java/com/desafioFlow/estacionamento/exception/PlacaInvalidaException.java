package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class PlacaInvalidaException extends RuntimeException {
	public PlacaInvalidaException(String message) {
		super(message);
	}
}
