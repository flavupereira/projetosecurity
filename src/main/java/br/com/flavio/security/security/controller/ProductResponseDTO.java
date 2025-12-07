package br.com.flavio.security.security.controller;

import br.com.flavio.security.security.domain.produto.Produto;

public record ProductResponseDTO(Long id, String nome, Integer price) {
	
	public ProductResponseDTO(Produto produto) {
		
		  this(produto.getId(), produto.getNome(), produto.getPrice());
	}

	
}
