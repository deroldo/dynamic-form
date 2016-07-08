# dynamic-form

[![Build Status](https://travis-ci.org/deroldo/dynamic-form.svg)](https://travis-ci.org/deroldo/dynamic-form)
[![Coverage Status](https://coveralls.io/repos/github/deroldo/dynamic-form/badge.svg)](https://coveralls.io/github/deroldo/dynamic-form)

## Sistema:
API REST de geração de formulários e respostas.

##Tecnologias utilizadas:
- Spring-boot
- Spring-data
- MongoDB 3.x
- Maven 3.x

##Testes:
- Mockito
- MockMvc
- Embedded mongo (test)

##Requisitos:
- Java 8
- MongoDB 3.x rodando no servidor da aplicação na porta default

##RUN:
No diretorio da aplicação executar:
```
mvn spring-boot:run
```
ou
```
mvn clean install
java -jar target/dynamic-form-0.0.1-SNAPSHOT.jar
```
