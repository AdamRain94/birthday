package ru.adamrain.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.RefreshToken; // Импорт сущности RefreshToken.
import ru.adamrain.main.exception.RefreshTokenException; // Импорт исключения, связанного с токенами обновления.
import ru.adamrain.main.repository.RefreshTokenRepository; // Импорт репозитория для работы с RefreshToken.

import java.time.Duration;
import java.time.Instant; // Импорт для работы с временем.
import java.util.Optional; // Импорт для использования Optional.
import java.util.UUID;

@Service
@RequiredArgsConstructor
// Сервис для работы с токенами обновления.
public class RefreshTokenService {

    @Value("${app.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration; // Длительность жизни токена обновления.

    private final RefreshTokenRepository refreshTokenRepository; // Репозиторий для работы с токенами обновления.

    // Метод для поиска токена обновления по значению токена.
    public Optional<RefreshToken> findByRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token); // Возвращаем найденный токен или пустой Optional.
    }

    // Метод для создания нового токена обновления для указанного пользователя.
    public RefreshToken createRefreshToken(Long userId) {
        // Создаем новый объект RefreshToken с заданным пользователем и временем истечения.
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis())) // Устанавливаем время истечения.
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken); // Сохраняем токен в репозитории.
        return refreshToken; // Возвращаем сохраненный токен.
    }

    // Метод для проверки токена обновления на истечение срока действия.
    public RefreshToken checkRefreshToken(RefreshToken token) {
        // Если токен просрочен, удаляем его и выбрасываем исключение.
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Repeat sign-in action");
        }

        return token; // Возвращаем действующий токен.
    }

    // Метод для удаления токенов обновления по идентификатору пользователя.
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId); // Удаляем токены обновления для указанного пользователя.
    }

}
