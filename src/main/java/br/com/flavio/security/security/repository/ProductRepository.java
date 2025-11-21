/**
 * 
 */
package br.com.flavio.security.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.flavio.security.security.domain.produto.Produto;

/**
 * @author User
 *
 */
public interface ProductRepository extends JpaRepository<Produto, String> {

}
