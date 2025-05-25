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

## Validações de Dados

Durante o cadastro ou atualização de um cliente, as seguintes validações são aplicadas:

- CPF:
  
  Obrigatório
  Deve conter 11 dígitos numéricos (\\d{11})
  Validação lógica via algoritmo oficial
  Não pode conter todos os dígitos iguais

- Nome:
  
  Obrigatório
  Deve conter apenas letras (maiúsculas/minúsculas) e espaços
  Tamanho entre 2 e 100 caracteres

- Idade:

  Obrigatório
  O cliente deve ter pelo menos 18 anos com base na data de nascimento (LocalDate)

- Endereço (Rua, Número, CEP):

  Rua não pode ser vazia
  Número deve ser maior que zero
  CEP deve estar no formato válido 00000-000

---

## Endpoints REST

**Clientes**
- POST    `/clientes`                           → Cadastrar cliente  
- GET     `/clientes`                           → Listar todos os clientes  
- GET     `/clientes/{id}`                      → Buscar cliente por ID  
- GET     `/clientes/nome?nome=Pedro`           → Buscar clientes por nome  
- PUT     `/clientes/{id}`                      → Atualizar cliente  
- DELETE  `/clientes/{id}`                      → Remover cliente  
- GET     `/clientes/{id}/info`                 → Informações básicas  

**Contas**
- POST    `/contas/corrente`                    → Criar conta corrente  
- POST    `/contas/poupanca`                    → Criar conta poupança  
- GET     `/contas`                             → Listar contas  
- GET     `/contas/{id}`                        → Detalhes da conta  
- PUT     `/contas/{id}/depositar?valor=100`    → Depositar  
- PUT     `/contas/{id}/sacar?valor=50`         → Sacar  
- GET     `/contas/{id}/saldo`                  → Consultar saldo  
- DELETE  `/contas/{id}`                        → Remover conta (saldo = 0)  
- PUT     `/contas/{id}/taxa-manutencao`        → Aplicar taxa manutenção  
- PUT     `/contas/{id}/rendimento`             → Aplicar rendimento mensal  

**Transferências**
- POST    `/transferencias/pix`                 → Transferência PIX  
- GET     `/transferencias/conta/{id}`          → Transferências da conta  
- GET     `/transferencias/{id}`                → Transferência por ID  

**Cartões**
- POST    `/cartoes/credito`                    → Criar cartão crédito  
- POST    `/cartoes/debito`                     → Criar cartão débito  
- GET     `/cartoes/{id}`                       → Buscar cartão  
- GET     `/cartoes/cliente/{clienteId}`        → Cartões por cliente  
- GET     `/cartoes/conta/{contaId}`            → Cartões por conta  
- PUT     `/cartoes/{id}/ativar`                → Ativar cartão  
- PUT     `/cartoes/{id}/desativar`             → Desativar cartão  
- PUT     `/cartoes/{id}/senha`                 → Alterar senha  
- PUT     `/cartoes/credito/{id}/gasto?valor=100`   → Adicionar gasto  
- GET     `/cartoes/credito/{id}/fatura`        → Consultar fatura  
- PUT     `/cartoes/credito/{id}/pagar`         → Pagar fatura  
- PUT     `/cartoes/credito/{id}/fechar`        → Fechar fatura com taxa  
- PUT     `/cartoes/credito/{id}/limite?valor=3000` → Ajustar limite crédito  
- PUT     `/cartoes/debito/{id}/pagamento?valor=50` → Pagar com débito  
- PUT     `/cartoes/debito/{id}/limite-diario?valor=500` → Ajustar limite diário  

**Seguros**
- POST    `/seguros/viagem/{idCartao}`          → Contratar seguro viagem  
- POST    `/seguros/fraude/{idCartao}`          → Contratar seguro contra fraude  
- GET     `/seguros/{id}`                       → Buscar seguro por ID  
- DELETE  `/seguros/{id}`                       → Cancelar seguro (exceto fraude)  
- GET     `/seguros/cliente/{clienteId}`        → Listar seguros do cliente  


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

---

## Autor
💻 Pedro Henrique Nunes
🌐 linkedin: https://www.linkedin.com/in/p-henrique-nunes
✉️ email: dinhonoliver@gmail.com
