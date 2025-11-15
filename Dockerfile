# Etapa 1: Construcción del JAR
#Dockerfile está dividido en dos capas
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY . .
RUN ./mvnw clean package -DskipTests

RUN ls -la /app/target

#Etapa 2: Construcción de la imagen final
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/CursITU/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/CursITU/app.jar"]