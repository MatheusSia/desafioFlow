package com.desafioFlow.estacionamento.controller.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {

	private String type;
	private String token;

	private TokenDTO() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Método builder
    public static TokenDTOBuilder builder() {
        return new TokenDTOBuilder();
    }

    // Classe interna para construção do TokenDTO
    public static class TokenDTOBuilder {
        private String type;
        private String token;

        private TokenDTOBuilder() {}

        public TokenDTOBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TokenDTOBuilder token(String token) {
            this.token = token;
            return this;
        }

        public TokenDTO build() {
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setType(type);
            tokenDTO.setToken(token);
            return tokenDTO;
        }
    }
}