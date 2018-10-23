# a simple account RESTful API

Provides a user with a CRUD service for managing simple accounts.

Uses embedded database and servlet container (microservices style)

Assumptions:

-	Assuming no security is required for this solution
-	Assuming only JSON will be supported


## Available operations:

- get all accounts ( RequestMethod=GET) http://localhost:8080/accounts
- add new Account via POST (RequestMethod=POST) http://localhost:8080/accounts
- delete an existing Account (RequestMethod=DELETE) http://localhost:8080/accounts/{id}

## To run the unit and integration tests, run:

./gradlew verify

## To run the application, run:

./gradlew bootRun


## Swagger documentation:

 http://localhost:8080/swagger-ui.html

