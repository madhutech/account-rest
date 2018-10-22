a simple account RESTful API that provides a user with a CRUD service for managing simple accounts.
Uses embedded database and servlet container (microservices style)


Available operations:
- get all accounts ( RequestMethod=GET) http://localhost:8080/accounts
- add new Account via POST (RequestMethod=POST) http://localhost:8080/accounts
- delete an existing Account (RequestMethod=DELETE) http://localhost:8080/accounts/{id}

To run the unit and integration tests, run:

./gradlew verify

To run the application, run:

./gradlew bootRun


Assumptions:

-	Assuming no security is required for this solution
-	Assuming only JSON will be supported


Swagger documentation: http://localhost:8080/swagger-ui.html

