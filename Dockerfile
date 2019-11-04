# Start with a base image containing Java runtime
FROM openjdk:11-jdk

# Add Maintainer Info
LABEL maintainer="rupesh.agrawal@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=urlshortner-1.0

# Add the application's jar to the container
#ADD ${JAR_FILE} urlshortner-1.0.jar

COPY target/dependencies/*.jar /apps/shortner-service/
COPY target/${JAR_FILE}.jar /apps/shortner-service/

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","/apps/shortner-service/*", "com.ragrawal.test.urlshortner.Application"]
