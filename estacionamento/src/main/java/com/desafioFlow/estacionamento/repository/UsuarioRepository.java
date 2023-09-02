package com.desafioFlow.estacionamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafioFlow.estacionamento.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findById(Integer id);
    
    @SuppressWarnings("unchecked")
	Usuario save(Usuario usuario);
}