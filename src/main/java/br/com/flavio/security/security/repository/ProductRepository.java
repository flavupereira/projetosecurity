/**
 * 
 */
package br.com.flavio.security.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.flavio.security.security.domain.produto.Produto;

/**
 * @author User
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Produto,Long> {

}
