FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/build/libs/*-all.jar app.jar
CMD ["java", "-jar", "app.jar"]
