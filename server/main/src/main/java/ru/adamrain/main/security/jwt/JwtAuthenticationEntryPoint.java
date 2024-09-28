package ru.adamrain.main.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
// Аннотация @Slf4j добавляет возможность логирования в классе с помощью Lombok.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Используем ObjectMapper для преобразования объекта в JSON.
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // Метод commence вызывается, когда требуется обработать несанкционированный доступ (401 Unauthorized).
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Логируем сообщение об ошибке авторизации.
        log.error("Unauthorized error: {}", authException.getMessage());

        // Устанавливаем тип контента ответа как JSON.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Устанавливаем HTTP статус ответа как 401 Unauthorized.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Создаем тело ответа в виде Map для отправки JSON ответа клиенту.
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // Статус ошибки
        body.put("error", "Unauthorized"); // Тип ошибки
        body.put("message", authException.getMessage()); // Сообщение об ошибке
        body.put("path", request.getServletPath()); // Путь, по которому произошла ошибка

        // Записываем JSON-ответ в OutputStream.
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
