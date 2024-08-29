# To-do List API

Este projeto consiste em uma API para gerenciamento de tarefas (to-do list) com um CRUD completo, desenvolvida em Java utilizando o framework Spring Boot.

## Funcionalidades

A API oferece as seguintes funcionalidades:

- **Autenticação e Autorização:** Utilização de JWT (JSON Web Tokens) para controle de login e acesso.
- **CRUD Completo de Tarefas:**
    - Criação de novas tarefas.
    - Leitura (listar todas as tarefas pendentes e/ou concluídas).
    - Atualização de tarefas existentes.
    - Exclusão de tarefas.
- **Validação de Dados:** Utilização do Spring Validation para garantir que os dados enviados sejam válidos.

## Tecnologias Utilizadas

- **Spring Boot:** Framework principal para o desenvolvimento da API.
- **Spring JPA:** Para persistência de dados no banco de dados.
- **Spring Validation:** Para validação de dados de entrada.
- **Spring Web:** Para criação e exposição dos endpoints REST.
- **JWT (JSON Web Token):** Para controle de login e autenticação segura.
- **PostgreSQL:** Banco de dados utilizado para armazenar as informações.

## Requisitos

- **Java 17:** Certifique-se de ter o JDK 17 instalado.
- **PostgreSQL:** Banco de dados PostgreSQL configurado e em execução.
- **Maven:** Para gerenciar as dependências do projeto.