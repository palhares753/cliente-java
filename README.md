# cliente-java-api

## About the API

An API for travel management. It is built with Java, Spring Boot, and Spring Framework. A toy-project to serve as a theoretical basis for the Medium series of articles I wrote about Java+Spring. The API main URL `/api-cliente/v1`.

## Features

This API provides HTTP endpoint's and tools for the following:

* Create a trip: `POST/api-cliente/v1/cliente`
* Update a trip: `PUT/api-cliente/v1/cliente`
* Delete a trip (by id): `DELETE/api-cliente/v1/cliente/1`
* Get report of cliente in a period of time (sorted and paginated): `GET/api-cliente/v1/cliente?cpf=xxxxxxxxxx`

### Details

`POST/api-cliente/v1/cliente`

This end-point is called to create a new trip.

**Body:**

```json
{
  "cpf": "xxxxxxxxxx",
  "nome": "ricardo palhares",
  "endereco": "rua sei la"
}
```


Returns an empty body with one of the following:

* 201 - Created: Everything worked as expected.
* 400 - Bad Request: the request was unacceptable, often due to missing a required parameter or invalid JSON.
* 404 - Not Found: The requested resource doesn't exist.
* 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
* 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than the end date.
* 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
* 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).

`PUT/api-cliente/v1/cliente/{id}`

This end-point is called to update a trip.

**Body:**

```json
{
   "id": 1,
   "cpf": "xxxxxxxxxx",
   "nome": "Ricardo Palhares",
   "endereco": "rua sei la"
}
```

Must be submitted the object that will be modified. Must return a trip specified by ID and all fields recorded above, including links and the one that was updated.

```json
{
   "data": {   
		"id": 1,
		"cpf": "xxxxxxxxxx",
		"nome": "Ricardo Palhares",
		"endereco": "rua sei la"
   		"links": [
			{
			"rel": "self",
				"href": "http://localhost:8080/api-cliente/v1/cliente/1"
			}
   		]
	}
}
```

`GET/api-cliente/v1/cliente?cpf=xxxxxxxxxx`

The end-point returns cliente were created within the period specified in the request. E.g., in the above query, we are looking for all cliente carried out between 01-18 January 2020. Also, the result should return in descending order and only page 2
with five trips.

`DELETE/api-cliente/v1/cliente/{id}`

This end-point causes a specific id to be deleted, accepting an empty request body and returning a 204 status code.


This end-point returns the statistics based on the cliente created.

**Returns:**

```json
{
	"data": { 
  		"cpf": "xxxxxxxxxx",
  		"nome": "Ricardo Palhares",
  		"Endereco": "Rua Sei la"
  		"links": [
			{
			"rel": "self",
				"href": "http://localhost:8080/api-cliente/v1/statistics/1"
			}
   		]
   	}
}
```
 
### Technologies used

This project was developed with:

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.3.7**
* **Spring Admin Client 2.3.1**
* **Maven**
* **JUnit 5**
* **Surfire**
* **PostgreSQL 13**
* **Flyway 6.4.4**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**
* **Heroku**
* **EhCache**
* **Bucket4j 4.10.0**
* **Partialize 20.05**

### Compile and Package

The API also was developed to run with an `jar`. In order to generate this `jar`, you should run:

```bash
mvn package
```

It will clean, compile and generate a `jar` at target directory, e.g. `cliente-java-api-5.0.1-SNAPSHOT.jar`

### Execution

You need to have **PostgreSQL 9.6.17 or above** installed on your machine to run the API on `dev` profile. After installed, on the `pgAdmin` create a database named `cliente`. If you don't have `pgAdmin` installed you can run on the `psql` console the follow command:

```sql
CREATE database cliente;
```

After creating the API database, you need to add your **Postgres** root `username` and `password` in the `application.properties` file on `src/main/resource`. The lines that must be modified are as follows:

```properties
spring.datasource.username=
spring.datasource.password=
```

When the application is running **Flyway** will create the necessary tables for the creation of the words and the execution of the compare between the end-points. In the test profile, the application uses **H2** database (data in memory).

### Test

* For unit test phase, you can run:

```bash
mvn test
```

* To run all tests (including Integration Tests):

```bash
mvn integration-test
```

### Run

In order to run the API, run the jar simply as following:

```bash
java -jar cliente-java-api-5.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
    
or

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

By default, the API will be available at [http://localhost:8080/api-cliente/v1](http://localhost:8080/api-cliente/v1)

### Documentation

* Swagger (development environment): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### License

This API is licensed under the MIT License.
