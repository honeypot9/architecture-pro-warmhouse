# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /workspace

# Копируем сначала только файлы для зависимостей (для кэширования)
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Устанавливаем Gradle и собираем приложение
RUN apk add --no-cache gradle && \
    gradle clean build -x test --no-daemon

# Stage 2: Create final image
FROM eclipse-temurin:17-jre-alpine

# Создаем не-root пользователя для безопасности
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

WORKDIR /app

# Копируем JAR файл из builder stage
COPY --from=builder --chown=spring:spring /workspace/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]