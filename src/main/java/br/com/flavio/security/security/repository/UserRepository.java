package br.com.flavio.security.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.flavio.security.security.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByLogin (String login);

}
