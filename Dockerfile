# 1. 빌드용 이미지
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar

# 2. 런타임 이미지
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
