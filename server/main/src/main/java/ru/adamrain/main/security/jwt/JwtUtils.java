package ru.adamrain.main.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.adamrain.main.security.AppUserDetails;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
// Класс JwtUtils отвечает за создание и проверку JWT токенов.
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret; // Секретный ключ для подписи JWT токенов.

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration; // Время жизни JWT токена.

    // Генерация JWT токена на основе данных пользователя.
    public String generateJwtToken(AppUserDetails userDetails) {
        return generateTokenFromUserTel(userDetails.getUsername());
    }

    // Генерация токена на основе номера телефона пользователя.
    public String generateTokenFromUserTel(String tel) {
        return Jwts.builder()
                .setSubject(tel) // Устанавливаем номер телефона как subject токена.
                .setIssuedAt(new Date()) // Устанавливаем дату создания токена.
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis())) // Устанавливаем срок действия токена.
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Подписываем токен с помощью HS512 и секретного ключа.
                .compact(); // Возвращаем сгенерированный токен.
    }

    // Извлечение номера телефона (subject) из токена.
    public String getUserTel(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // Проверка валидности токена.
    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken); // Проверяем подпись токена.
            return true; // Токен валиден.
        } catch (SignatureException exception) {
            log.error("invalid signature: {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("invalid token: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            log.error("invalid expired: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("invalid unsupported: {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("invalid string is empty: {}", exception.getMessage());
        }
        return false; // Токен не валиден.
    }
}
