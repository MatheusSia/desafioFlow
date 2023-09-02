package com.desafioFlow.estacionamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafioFlow.estacionamento.controller.form.VeiculoForm;
import com.desafioFlow.estacionamento.exception.ErrorResponse;
import com.desafioFlow.estacionamento.exception.PlacaInvalidaException;
import com.desafioFlow.estacionamento.exception.TipoVeiculoInvalidoException;
import com.desafioFlow.estacionamento.modelo.Veiculo;
import com.desafioFlow.estacionamento.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoService veiculoService;

	
	@GetMapping
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarTodosVeiculos();
        return ResponseEntity.ok(veiculos);
    }
	
	
	@PostMapping
	public ResponseEntity<?> registrarEntrada(@RequestBody VeiculoForm form) {
	    try {
	        Veiculo veiculo = veiculoService.registrarEntrada(form.getPlaca(), form.getTipo());
	        return ResponseEntity.ok(veiculo);
	    } catch (PlacaInvalidaException | TipoVeiculoInvalidoException e) {
	        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}

	
}
