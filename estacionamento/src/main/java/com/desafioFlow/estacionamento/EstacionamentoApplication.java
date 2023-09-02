package com.desafioFlow.estacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EstacionamentoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
//		String result = bCryptPasswordEncoder.encode("12345678");
//		System.out.println("\n\n My hash: " + result);
		SpringApplication.run(EstacionamentoApplication.class, args);
	}

}
