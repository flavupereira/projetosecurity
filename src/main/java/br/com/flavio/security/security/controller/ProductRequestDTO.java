package br.com.flavio.security.security.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDTO {
	
	@NotBlank
	String nome;
	
	@NotNull
	Integer price;

}
