# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -B -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app
# Cambia el nombre del jar si tu artefacto no se llama ticketfix
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
