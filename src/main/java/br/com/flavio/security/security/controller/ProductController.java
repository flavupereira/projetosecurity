package br.com.flavio.security.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.flavio.security.security.domain.produto.Produto;
import br.com.flavio.security.security.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("product")
public class ProductController {
	
	    @Autowired
	    ProductRepository repository;
	    
	    
	    @PostMapping
	    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body) {
	    	
	    	Produto novoProduto = new Produto();
	    	
	    	this.repository.save(novoProduto);
	    	
	    	return ResponseEntity.ok().build();
	    }
	    
	    
	    
	    @GetMapping
	    public ResponseEntity getAllProducts() {
	    	
	    	List<ProductResponseDTO> productList = 
	    			this.repository.findAll().stream().map(roductResponseDTO::new).toList())
	    }

}
