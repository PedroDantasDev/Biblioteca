# Biblioteca EloTech

Este projeto é uma aplicação de gerenciamento de biblioteca desenvolvida com Spring Boot (backend) e React (frontend).

## Pré-requisitos

- Java Development Kit (JDK) 21
- Node.js (versão 14 ou superior) e npm
- Maven 3.6+
-  H2 DB
- Git (para clonar o repositório)

## Configuração e Execução

Navegue até a pasta do projeto backend:
bashCopycd caminho/para/projeto-backend


Para o H2 DB, use estas configurações:

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true



Compile o projeto:
mvn clean install

Execute o projeto:
mvn spring-boot:run
O backend estará rodando em http://localhost:8080.

Frontend (React)

Abra um novo terminal e navegue até a pasta do projeto frontend:
cd caminho/para/projeto-frontend

Instale as dependências:
npm install

Inicie o servidor de desenvolvimento:
npm start
O frontend estará rodando em http://localhost:3000.

Use a interface para navegar entre as diferentes funcionalidades:

Gerenciamento de Livros
Gerenciamento de Usuários
Gerenciamento de Empréstimos
Recomendações de Livros
Busca no Google Books