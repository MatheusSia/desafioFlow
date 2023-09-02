package com.desafioFlow.estacionamento.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "veiculos")
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "veiculo_id")
	private Long id;

	@Column(nullable = false, name = "placa")
	private String placa;

	@Column(nullable = false, name = "tipo")
	private TipoVeiculo tipo;

	@Column(nullable = false, name = "horario_entrada")
	private LocalDateTime horarioEntrada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getHorarioEntrada() {
		return horarioEntrada;
	}

	public void setHorarioEntrada(LocalDateTime horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

}
