package ru.adamrain.main.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;
import ru.adamrain.main.entity.RefreshToken;

@Component
@Slf4j
// Аннотация @Slf4j добавляет возможность логирования в этом классе с использованием библиотеки Lombok.
public class RedisExpirationEvent {

    @EventListener
    // Аннотация @EventListener позволяет обработать событие, которое произойдет в контексте приложения (в данном случае — истечение ключа в Redis).
    public void handleRedisKeyExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event) {
        // Метод handleRedisKeyExpiredEvent срабатывает, когда происходит событие истечения срока действия ключа Redis.
        RefreshToken refreshToken = (RefreshToken) event.getValue();
        // Получаем объект RefreshToken из события.

        if (refreshToken == null) {
            // Если объект refreshToken равен null, выбрасываем исключение, чтобы предупредить о проблеме.
            throw new RuntimeException("Refresh token is null in handleRedisKeyExpiredEvent function!");
        }

        // Логируем сообщение, что токен обновления истек, с указанием его ключа (ID) и самого токена.
        log.info("Refresh token with key={} has expired! Refresh token is : {}", refreshToken.getId(), refreshToken.getToken());
    }
}
