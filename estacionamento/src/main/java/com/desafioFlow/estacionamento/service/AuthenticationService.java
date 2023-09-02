package com.desafioFlow.estacionamento.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.desafioFlow.estacionamento.modelo.Usuario;
import com.desafioFlow.estacionamento.repository.UserRepositoryPort;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UserRepositoryPort repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = repository.findByUsername(username);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		throw new UsernameNotFoundException("User not found");
	}	

}