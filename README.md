# Finapp Títulos API – Spring Boot + Oracle

API REST para gestão de títulos financeiros (contas a pagar e a receber), integrada ao banco de dados Oracle FIN_APP.

>  Status: MVP em desenvolvimento


## Stack utilizada

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **Oracle Database 21c XE**
- **Implementação futura: Spring Validation, Spring Security**


## Estrutura geral do projeto
```markdown
finapp-titulos-api/
├── src/
│   ├── main/
│   │   ├── java/com/gustavo/finapp/finapp_titulos_api/
│   │   │   ├── controller/
│   │   │   │   └── TituloController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── ResumoTitulosResponse.java
│   │   │   │   ├── TituloCobrancaResponse.java
│   │   │   │   ├── TituloDto.java
│   │   │   │   └── TituloPageResponse.java
│   │   │   │
│   │   │   └── repository/
│   │   │       └── TituloRepository.java
│   │   │
│   │   └── FinappTitulosApiApplication.java
│   │
│   └── resources/
│       ├── application.properties
│       └── ...
│
└── README.md
