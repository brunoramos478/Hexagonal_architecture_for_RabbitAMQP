FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src

RUN mvn -B package -DskipTests

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN apk add --no-cache wget

RUN addgroup -S fusion && adduser -S fusion -G fusion

# Copia jar
COPY --from=build --chown=fusion:fusion /build/target/*.jar app.jar

USER fusion

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
 CMD wget -qO- http://localhost:8082/actuator/health/readiness || exit 1

ENTRYPOINT ["java","-jar","app.jar"]