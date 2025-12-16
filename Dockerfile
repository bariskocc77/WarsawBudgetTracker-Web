# 1. Aşama: Build (İnşaat) Aşaması
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Aşama: Run (Çalıştırma) Aşaması
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/budget-tracker-0.0.1-SNAPSHOT.jar budget-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","budget-tracker.jar"]
