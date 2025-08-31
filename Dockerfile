# Use OpenJDK 11
FROM openjdk:11-jre-slim

# Instalar Maven
FROM maven:3.8.1-openjdk-11-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar pom.xml primeiro para aproveitar cache do Docker
COPY pom.xml .

# Baixar dependências
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar aplicação
RUN mvn clean package -DskipTests

# Fase final - runtime
FROM openjdk:11-jre-slim

WORKDIR /app

# Copiar JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Comando para iniciar aplicação
CMD ["java", "-jar", "app.jar"]