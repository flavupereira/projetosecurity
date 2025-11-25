package br.com.flavio.security.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.flavio.security.security.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	UserDetails findbylogin (String login);

}
