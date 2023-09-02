package com.desafioFlow.estacionamento.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafioFlow.estacionamento.controller.form.NotaMoedaEstoqueForm;
import com.desafioFlow.estacionamento.modelo.NotaMoedaEstoque;
import com.desafioFlow.estacionamento.service.NotaMoedaEstoqueService;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

	@Autowired
	private NotaMoedaEstoqueService estoqueService;

	@GetMapping
	public ResponseEntity<List<NotaMoedaEstoque>> obterEstoque() {
		List<NotaMoedaEstoque> estoque = estoqueService.obterEstoque();
		System.out.print(ResponseEntity.ok(estoque));
		return ResponseEntity.ok(estoque);
	}

	@PostMapping("/armazenar-dinheiro")
	public ResponseEntity<NotaMoedaEstoque> armazenarDinheiro(@RequestBody NotaMoedaEstoqueForm form) {
		NotaMoedaEstoque notaMoedaEstoque = estoqueService.armazenarDinheiro(form);
		return ResponseEntity.ok(notaMoedaEstoque);
	}

}
