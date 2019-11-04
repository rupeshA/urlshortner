# Description
Most of us are familiar with seeing URLs like bit.ly or t.co on our Twitter or Facebook feeds. These are examples of shortened URLs, which are a short alias or pointer to a longer page link. For example, I can send you the shortened URL http://bit.ly/SaaYw5 that will forward you to a very long Google URL with search results on how to iron a shirt.

# Dev Setup:
## Prerequisites
* Java JDK 11.0.2
* Apache Maven 3.5.4+
* Git
* IDE With JDK 11 Support


## Running the application locally
Export or set following properties to connect to a postgres database, and then run teh subsequent commands to run it locally
* spring.datasource.username=${DB_USER}
* spring.datasource.password=${DB_PASSWORD}
* spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}

```
mvn clean install
mvn clean spring-boot:run
```
Once run the application will be up at http://localhost:8080/ and swagger file will be available at http://localhost:8080/swagger-ui.html

# Building and running Docker image
## build the docker image explicitly
```
docker build -t urlshortner
```
## run the docker image(with required parameters)
```
docker run -e DB_HOST='<db_host>' -e DB_PORT='<db_port>' -e DB_NAME='<databaseName>' -e DB_USER='<databse User>' -e DB_PASSWORD='<dataBase password>' -p 5000:8080 urlshortner
```
e.g
```
docker run -e DB_HOST='localhost' -e DB_PORT='5432' -e DB_NAME='shortnerdb' -e DB_USER='postgres' -e DB_PASSWORD='postgres' -p 5000:8080 urlshortner

```
