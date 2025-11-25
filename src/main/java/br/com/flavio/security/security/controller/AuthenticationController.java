package br.com.flavio.security.security.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.flavio.security.security.domain.user.AuthenticationDTO;
import br.com.flavio.security.security.domain.user.LoginResponseDTO;
import br.com.flavio.security.security.domain.user.RegisterDTO;
import br.com.flavio.security.security.domain.user.User;
import br.com.flavio.security.security.infra.TokenService;
import br.com.flavio.security.security.repository.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@PostMapping("/register") 
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		
		if(this.userRepository.findbylogin(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		  String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		  User newUser  = new User (data.login() , encryptedPassword , data.role());
		  
		  this.userRepository.save(newUser);
		return ResponseEntity.ok().build();
		
	}
}
