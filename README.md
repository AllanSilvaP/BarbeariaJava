## Barbearia Aguia Real - Introdução

- O projeto consiste em um sistema para uma barbearia real denominado **Barbearia Aguia Real**.
- Foi implementado utilizando **Java** como linguagem de programação, com integração a um banco de dados relacional **MySQL**.

## Descrição do Projeto

- A arquitetura do projeto segue o padrão **DAO (Data Access Object)**, **Controller** e **Model**, proporcionando uma modularização coerente e de fácil compreensão, mesmo com a presença de um volume significativo de código.
- O sistema permite a criação de contas para diferentes tipos de usuários (funcionários e clientes). 
- Funcionários possuem acesso a funcionalidades administrativas, como:
  - Gerenciar Estoque;
  - Gerenciar Barbeiro;
  - Gerenciar Serviços;
  - Gerenciar Agendamentos;
  - Entre outras operações.
- Clientes possuem acesso ao painel de agendamento ondem podem deixar agendado o seu serviço

## Como Configurar o Projeto Localmente

- Para executar o projeto localmente, siga os passos abaixo:

1. **Adicionar Bibliotecas Necessárias:**
   - Certifique-se de adicionar as bibliotecas (libraries) requeridas à IDE utilizada. O processo de referência das bibliotecas pode variar dependendo da IDE escolhida.
   - Dica: Observe a pasta **Lib** quais sãos as libraries utilizadas

2. **Configurar Conexão com o Banco de Dados:**
   - Edite o arquivo `ConectaDB.java`, localizado na pasta **DAO**, ajustando o trecho relacionado às variáveis de conexão:

```java
// Variáveis para conexão
private static final String url = "jdbc:mysql://db-barbearia-aguia-real.c7miwyc2szll.us-east-2.rds.amazonaws.com:3306/Barbearia";
private static final String username = "Allan";
private static final String password = "Banco.top";
```

- Substitua:
  - **`url`** pelo link do seu servidor de banco de dados;
  - **`user`** pelo nome de usuário para acesso ao banco de dados;
  - **`password`** pela senha correspondente.

3. **Executar a Classe Principal:**
   - Navegue até a classe `Main.java`, localizada na pasta **App**, e execute o arquivo para iniciar o sistema.


