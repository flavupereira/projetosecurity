src/main/java/br/com/flavio/security/
├── 📁 controller/
│   ├── AuthenticationController.java     # Login e registro
│   └── ProductController.java           # CRUD produtos
├── 📁 domain/
│   ├── 📁 user/
│   │   ├── User.java                    # Entidade usuário
│   │   ├── UserRole.java                # Enum de roles
│   │   ├── AuthenticationDTO.java       # DTO login
│   │   ├── LoginResponseDTO.java        # DTO resposta login
│   │   └── RegisterDTO.java             # DTO registro
│   └── 📁 produto/
│       ├── Produto.java                 # Entidade produto
│       ├── ProductRequestDTO.java       # DTO request produto
│       └── ProductResponseDTO.java      # DTO response produto
├── 📁 infra/
│   ├── SecurityConfigurations.java      # Config segurança
│   ├── SecurityFilter.java              # Filtro JWT
│   └── TokenService.java                # Serviço JWT
├── 📁 repository/
│   ├── UserRepository.java              # Repositório usuários
│   └── ProductRepository.java           # Repositório produtos
└── 📁 services/
    └── AuthorizationService.java        # UserDetailsService
