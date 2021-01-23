# Build stage
FROM maven:3.6.0-jdk-8-alpine AS builder
WORKDIR /app
COPY . /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

# Package stage
FROM openjdk:8-jdk-alpine
COPY --from=builder /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]