FROM eclipse-temurin:21-alpine
LABEL authors="Tountoun AYANOU"
LABEL maintainer="tountounabela@gmail.com"
WORKDIR /app

COPY target/transfer.jar /app/app.jar

EXPOSE 6070

ENTRYPOINT ["java", "-jar", "app.jar"]