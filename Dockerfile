# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
RUN mkdir /opt/app
COPY --from=build /app/application/target/application-0.0.5.jar /opt/app
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "application-0.0.5.jar"]
EXPOSE 8080
