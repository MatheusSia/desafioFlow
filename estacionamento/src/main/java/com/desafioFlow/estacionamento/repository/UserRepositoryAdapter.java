package com.desafioFlow.estacionamento.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafioFlow.estacionamento.modelo.Usuario;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort{
	
	@Autowired
	private UsuarioRepository authRepository;

	@Override
	public Usuario save(Usuario user) {
		return authRepository.save(user);
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {
		return authRepository.findByUsername(username);
	}
	
	@Override
	public Optional<Usuario> findById(Long id){
		return authRepository.findById(id);
	}

}