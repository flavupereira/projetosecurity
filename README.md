# 🔐 Spring Boot Security - Sistema de Autenticação JWT

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot)](https://spring.io)
[![Security](https://img.shields.io/badge/Spring-Security-blue?style=for-the-badge&logo=springsecurity)](https://spring.io/projects/spring-security)

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
| **JWT** | 0.9.1 | Tokens de autenticação |
| **Spring Data JPA** | 3.x | Persistência de dados |
| **Lombok** | - | Redução de boilerplate |
| **Validation API** | - | Validação de dados |
| **H2 Database** | - | Banco em memória (dev) |

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


## 🔄 Fluxo de Autenticação

### 1. **Registro de Usuário**
Cliente → POST /auth/register → Valida DTO → Criptografa senha → Salva usuário → Retorna 200 OK

### 2. **Login e Geração de Token**
Cliente → POST /auth/login → AuthenticationManager → AuthorizationService →
UserRepository → TokenService → Gera JWT → Retorna token


### 3. **Acesso a Endpoint Protegido**
Requisição → SecurityFilter → Extrai token → TokenService.validaToken →
Busca usuário → Configura SecurityContext → Verifica autorização → Processa requisição



## ⚙️ Configuração

### application.properties
```properties
# Segurança
api.security.token.secret=seu-segredo-super-secreto-aqui

# Banco de dados (H2 - Desenvolvimento)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

## ⚙️ Configuração

### application.properties
```properties
# Segurança
api.security.token.secret=seu-segredo-super-secreto-aqui

# Banco de dados (H2 - Desenvolvimento)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

# JPA
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

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
Validação de credenciais

Tokens JWT com expiração

Senhas criptografadas (BCrypt)

Filtro de segurança personalizado

✅ Autorização
Controle de acesso baseado em roles

Hierarquia de permissions (ADMIN > USER)

Proteção de endpoints específicos

✅ Validação
Bean Validation em DTOs

Validação de token JWT

Verificação de usuário único

✅ Configuração
CSRF desabilitado para APIs

Sessões stateless

CORS configurável


