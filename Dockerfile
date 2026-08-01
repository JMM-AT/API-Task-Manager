FROM eclipse-temurin:21-jdk-alpine
COPY target/*.jar api-Task-Manager.jar
ENTRYPOINT ["java","-jar","api-Task-Manager.jar"]