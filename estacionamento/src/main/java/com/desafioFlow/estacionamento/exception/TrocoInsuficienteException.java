package com.desafioFlow.estacionamento.exception;

@SuppressWarnings("serial")
public class TrocoInsuficienteException extends RuntimeException {
	public TrocoInsuficienteException(String message) {
		super(message);
	}
}