FROM openjdk:17
EXPOSE 9002
ARG JAR_FILE=target/*.jar
COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]