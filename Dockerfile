FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/api-maquinas-0.0.1-SNAPSHOT.jar app.jar
USER 10001:10001
ENV PORT=8080 JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=50 -XX:+UseSerialGC -XX:ActiveProcessorCount=2"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
