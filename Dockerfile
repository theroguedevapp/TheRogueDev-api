# Stage 1: Build
FROM gradle:8.12-jdk21 AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./
RUN find . -name "*.jar" ! -name "*plain*" -exec mv {} app.jar \; && \
    find . -name "*plain*" -delete
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
