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

Additionally following property is required to create the redirection URL by the system and also to filterout a request which tries to shorten the system's shortening URL.
* hosted.url=${HOSTED_URL}
for example if the system is runing on localhost, its value need to be 'http://www.localhost:8080/rd/' 

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
Important:Using an input file is preferred for properties, as it will have sensitive information(username/password etc)
```
docker run -e DB_HOST='<db_host>' -e DB_PORT='<db_port>' -e DB_NAME='<databaseName>' -e DB_USER='<databse User>' -e DB_PASSWORD='<dataBase password>' -e HOSTED_URL='http://<HOST_MACHINE>:5000/rd/ -p 5000:8080 urlshortner
```
e.g
```
docker run -e DB_HOST='localhost' -e DB_PORT='5432' -e DB_NAME='shortnerdb' -e DB_USER='postgres' -e DB_PASSWORD='postgres' -p 5000:8080 -e HOSTED_URL='http://vm-aus-cbl.com:5000/rd/ urlshortner

```
Important : Using an input file is preferred for properties, as it will have sensitive information(username/password etc)
e.g.
```
docker run --env-file ./env.list urlshortner
```
## TroubleShooting
Clear docker cache using following command, in case the changes in code/properties seems to be not getting picked up by the latest image run
```
docker system prune
```
re-create docker image using following command, and try re-running
```
docker build -t urlshortner
```
