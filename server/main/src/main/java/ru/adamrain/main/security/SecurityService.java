package ru.adamrain.main.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.RefreshToken;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.exception.RefreshTokenException;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.security.jwt.JwtUtils;
import ru.adamrain.main.service.RefreshTokenService;
import ru.adamrain.main.web.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
// Сервисный класс для обработки аутентификации, регистрации пользователей и управления токенами.
public class SecurityService {

    private final AuthenticationManager authenticationManager; // Менеджер аутентификации для обработки аутентификации.
    private final JwtUtils jwtUtils; // Утилиты для работы с JWT токенами.
    private final RefreshTokenService refreshTokenService; // Сервис для управления refresh токенами.
    private final UserRepository userRepository; // Репозиторий для работы с пользователями.
    private final PasswordEncoder passwordEncoder; // Кодировщик паролей.

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        // Аутентификация пользователя с помощью предоставленных учетных данных.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getTel(), // Получаем телефон пользователя.
                loginRequest.getPassword() // Получаем пароль пользователя.
        ));

        // Установка аутентификации в контекст безопасности.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal(); // Получаем детали пользователя.

        // Получаем роли пользователя и преобразуем их в список строк.
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Создаем новый refresh токен для пользователя.
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        // Возвращаем объект AuthResponse с данными аутентификации.
        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails)) // Генерируем JWT токен.
                .refreshToken(refreshToken.getToken()) // Добавляем refresh токен в ответ.
                .tel(userDetails.getUsername()) // Добавляем телефон пользователя.
                .roles(roles) // Добавляем роли пользователя.
                .build();
    }

    public void register(CreateUserRequest createUserRequest) {
        // Создание нового пользователя и сохранение его в базе данных.
        User user = User.builder()
                .tel(createUserRequest.getTel()) // Устанавливаем телефон.
                .name(createUserRequest.getName()) // Устанавливаем имя.
                .password(passwordEncoder.encode(createUserRequest.getPassword())) // Кодируем пароль перед сохранением.
                .roles(createUserRequest.getRoles()) // Устанавливаем роли.
                .build();

        userRepository.save(user); // Сохраняем пользователя в базе данных.
    }

    public RefreshTokenResponse refreshTokenResponse(RefreshTokenRequest refreshTokenRequest) {
        // Получаем refresh токен из запроса.
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByRefreshToken(refreshToken) // Находим refresh токен в базе данных.
                .map(refreshTokenService::checkRefreshToken) // Проверяем действительность refresh токена.
                .map(RefreshToken::getUserId) // Получаем ID пользователя, которому принадлежит токен.
                .map(userId -> {
                    User tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                            new RefreshTokenException("Exeption trying to get token for userId: " + userId)); // Получаем пользователя по ID.

                    // Генерируем новый JWT токен для пользователя.
                    String token = jwtUtils.generateTokenFromUserTel(tokenOwner.getTel());

                    // Возвращаем новый токен и refresh токен в ответе.
                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(refreshToken, "RefreshToken not found")); // Если refresh токен не найден, выбрасываем исключение.
    }

    public void logout() {
        // Метод для выхода пользователя, удаляющий его refresh токены.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            Long userId = userDetails.getId(); // Получаем ID пользователя из контекста.
            refreshTokenService.deleteByUserId(userId); // Удаляем все refresh токены, связанные с пользователем.
        }
    }
}
