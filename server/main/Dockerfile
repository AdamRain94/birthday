# 1. Указываем базовый образ с установленным JDK (например, JDK 17)
FROM openjdk:17-jdk-slim

# 2. Указываем рабочую директорию внутри контейнера
WORKDIR /app

# 3. Копируем скомпилированный .jar файл приложения в контейнер
# Важно: замените 'my-spring-app.jar' на имя вашего .jar файла
COPY target/main-0.0.1-SNAPSHOT.jar /app/main-0.0.1-SNAPSHOT.jar

# 4. Открываем порт 8080 для работы приложения
EXPOSE 8080

# 5. Указываем команду для запуска Spring Boot-приложения
ENTRYPOINT ["java", "-jar", "/app/main-0.0.1-SNAPSHOT.jar"]
