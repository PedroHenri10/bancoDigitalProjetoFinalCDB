# Banco Digital - Projeto Final CDB

Este projeto é uma API REST de um sistema bancário digital desenvolvida com **Spring Boot**, simulando operações como abertura de contas, emissão de cartões, transferências PIX, fatura de cartão de crédito, aplicação de taxas e seguros.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Postman / Insomnia
- Maven

---

## Funcionalidades

- Cadastro de Clientes com validação de CPF, nome, idade e endereço
- Criação de contas Corrente e Poupança  
  - Corrente com taxa de manutenção mensal  
  - Poupança com rendimento mensal (juros compostos)
- Emissão de cartões de crédito e débito  
  - Débito com limite diário ajustável  
  - Crédito com limite baseado no tipo de cliente
- Fatura de cartão com taxa de utilização
- Seguros de viagem e fraude
- Transferência entre contas via PIX
- Depósito e Saque
- Atualização automática da categoria do cliente (COMUM, SUPER, PREMIUM)

---

## Estrutura do Projeto

bancoDigitalProjetoFinalCDB ├── src │   └── main │       ├── java │       │   └── br.com.cdb.bancoDigitalProjetoFinalCDB │       │       ├── controller │       │       ├── entity │       │       │   ├── enums │       │       ├── exception │       │       ├── repository │       │       └── service │       └── resources │           ├── application.properties │           └── ... ├── pom.xml └── README.md

---

## Endpoints

Veja a documentação básica de testes no Postman:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/clientes` | Cadastrar cliente |
| GET  | `/clientes` | Listar todos os clientes |
| POST | `/contas/corrente` | Criar conta corrente |
| POST | `/contas/poupanca` | Criar conta poupança |
| PUT  | `/contas/{id}/depositar` | Depositar na conta |
| PUT  | `/contas/{id}/sacar` | Sacar da conta |
| POST | `/transferencias/pix` | Realizar transferência PIX |
| POST | `/cartoes/credito` | Criar cartão crédito |
| PUT  | `/cartoes/credito/{id}/gasto` | Adicionar gasto |
| GET  | `/cartoes/credito/{id}/fatura` | Consultar fatura |
| PUT  | `/cartoes/credito/{id}/pagar` | Pagar fatura |

---

## Como Rodar o Projeto

1. Clonar o Repositório

git clone https://github.com/seuusuario/seurepositorio.git


2. Importar no Eclipse

Vá em File > Import

Escolha Existing Maven Projects

Selecione a pasta do projeto clonado

Finalize com Finish


3. Executar a Aplicação

Localize a classe BancoDigitalProjetoFinalCdbApplication

Clique com o botão direito > Run As > Java Application


4. Testar com Postman ou Insomnia

A aplicação estará rodando em:
http://localhost:8080

---

## Configuração do Banco de Dados (MySQL)

Crie um banco chamado `banco_digital` no MySQL.

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:8080/banco_digital
spring.datasource.username=root/ou seu usuário
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
