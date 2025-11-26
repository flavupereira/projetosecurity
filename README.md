# 🔐 Spring Boot Security - Sistema de Autenticação JWT

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring-Security-blue?style=for-the-badge&logo=springsecurity)

Um sistema completo de autenticação e autorização usando **Spring Security** com **JWT** (JSON Web Token), implementando controle de acesso baseado em roles e proteção de endpoints.

## 📋 Índice

- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Fluxo de Autenticação](#-fluxo-de-autenticação)
- [Configuração](#-configuração)
- [Como Usar](#-como-usar)
- [Endpoints](#-endpoints)
- [Segurança](#-segurança)

## 🛠 Tecnologias

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 17+ | Linguagem de programação |
| **Spring Boot** | 3.x | Framework principal |
| **Spring Security** | 3.x | Autenticação e autorização |
| **JWT** | 4.5.0 | Tokens de autenticação |
| **Spring Data JPA** | 3.x | Persistência de dados |
| **PostgreSQL** | - | Banco de dados |
| **Lombok** | - | Redução de boilerplate |
| **Validation API** | - | Validação de dados |
| **Flyway** | 9.22.0 | Migração de banco |

## ✨ Funcionalidades

### 🔐 Autenticação & Autorização
- ✅ Registro de usuários com roles (ADMIN/USER)
- ✅ Login com geração de token JWT
- ✅ Autorização baseada em roles
- ✅ Senhas criptografadas com BCrypt
- ✅ Tokens com expiração (2 horas)

### 🛡️ Segurança
- ✅ Filtro de segurança personalizado
- ✅ Proteção CSRF desabilitada (APIs)
- ✅ Sessões stateless
- ✅ Validação de entrada com Bean Validation

### 📦 Gerenciamento de Produtos

- ✅ CRUD de produtos
- ✅ Controle de acesso por role
- ✅ DTOs para request/response
- ✅ Validação de dados

## 🔄 Fluxo de Autenticação com Spring Security

### 1. **Configuração do Spring Security**

A classe `SecurityConfigurations` configura toda a segurança da aplicação:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessões stateless
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}

## 2. Registro de Usuário
java
@PostMapping("/register") 
public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
    if(this.userRepository.findbylogin(data.login()) != null) {
        return ResponseEntity.badRequest().build();
    }
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.login(), encryptedPassword, data.role());
    this.userRepository.save(newUser);
    return ResponseEntity.ok().build();
}

### Processo:
Valida se o usuário já existe
Criptografa a senha com BCrypt
Cria e salva o novo usuário com role específica


## 3. Login e Geração de Token JWT

java
@PostMapping("/login")
public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
}

### Processo:
Cria UsernamePasswordAuthenticationToken com credenciais
AuthenticationManager valida as credenciais
TokenService gera JWT com subject (login) e expiração
Retorna token para o cliente

## 4. Filtro de Segurança Personalizado

A classe SecurityFilter intercepta todas as requisições:

java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if(token != null) {
        var login = tokenService.validateToken(token);
        UserDetails user = userRepository.findbylogin(login);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
}

### Processo:
Extrai token do header Authorization
Valida token com TokenService
Busca usuário no repositório
Configura autenticação no SecurityContextHolder

## 5. Geração e Validação de Token JWT

java
public String generateToken(User user) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
            .withIssuer("auth-api")
            .withSubject(user.getLogin())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);
        return token;
    } catch (JWTCreationException exception) {
        throw new RuntimeException("Error while generating token", exception);
    }
}

## **⚙️ Configuração**
###application.properties

properties
# Datasource
spring.datasource.url=jdbc:postgresql://localhost:5432/product
spring.datasource.username=***
spring.datasource.password=***

# JWT Secret
api.security.token.secret=${JWT_SECRET:my-secret-key}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true


##**🚀 Como Usar**
###1. Registrar Usuário

curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","password":"123456","role":"ADMIN"}'

## 2. Fazer Login

bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","password":"123456"}'

##3. Acessar Endpoint Protegido

bash
curl -X GET http://localhost:8080/product \
  -H "Authorization: Bearer <seu-token-jwt>"

📡 Endpoints
🔓 Públicos
Método	Endpoint	Descrição
POST	/auth/register	Registrar novo usuário
POST	/auth/login	Fazer login
🔐 Protegidos
Método	Endpoint	Role	Descrição
GET	/product	USER, ADMIN	Listar produtos
POST	/product	ADMIN	Criar produto


🛡️ Segurança Implementada
✅ Autenticação
Validação de credenciais com AuthenticationManager

Tokens JWT com expiração (2 horas)

Senhas criptografadas com BCrypt

Filtro de segurança personalizado

✅ Autorização
Controle de acesso baseado em roles (UserRole.ADMIN, UserRole.USER)

Hierarquia de permissions (ADMIN tem ROLE_ADMIN + ROLE_USER)

Proteção de endpoints específicos com hasRole()

✅ Validação
Bean Validation em DTOs (@NotBlank, @NotNull)

Validação de token JWT com assinatura HMAC256

Verificação de usuário único no registro

✅ Configuração Spring Security
CSRF desabilitado para APIs REST

Sessões stateless

Filtro personalizado antes do filtro de autenticação padrão

Configuração granular de autorização por endpoint

✅ UserDetails Implementation
A entidade User implementa UserDetails:

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == UserRole.ADMIN)
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    else
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
}
