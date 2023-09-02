package com.desafioFlow.estacionamento.repository;

import java.util.Optional;

import com.desafioFlow.estacionamento.modelo.Usuario;

public interface UserRepositoryPort {
	
	Optional<Usuario> findById(Long id);
	
	Optional<Usuario> findByUsername(String username);
	
	Usuario save(Usuario usuario);

}