package br.com.flavio.security.security.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {

}
