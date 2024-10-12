package ru.adamrain.main.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.RefreshToken;
import ru.adamrain.main.entity.RoleType;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.exception.AlreadyExistException;
import ru.adamrain.main.exception.RefreshTokenException;
import ru.adamrain.main.exception.UserTelNotFoundExcepion;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.security.jwt.JwtUtils;
import ru.adamrain.main.service.PhoneNumberService;
import ru.adamrain.main.service.RefreshTokenService;
import ru.adamrain.main.web.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager; // Менеджер аутентификации для обработки аутентификации.
    private final JwtUtils jwtUtils; // Утилиты для работы с JWT токенами.
    private final RefreshTokenService refreshTokenService; // Сервис для управления refresh токенами.
    private final UserRepository userRepository; // Репозиторий для работы с пользователями.
    private final PasswordEncoder passwordEncoder; // Кодировщик паролей.
    private final PhoneNumberService phoneNumberService;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        String tel = phoneNumberService.valid(loginRequest.getTel());
        if (tel == null) return ResponseEntity.status(400).body("Невалидный номер телефона!");

        User user = userRepository.findByTel(tel).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("Пользователь не найден!");

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return ResponseEntity.status(400).body("Неверный пароль!");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                tel,
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok().body(AuthResponse.builder()
                .id(user.getId())
                .fam(user.getFam())
                .name(user.getName())
                .otch(user.getOtch())
                .dateOfBirth(user.getDateOfBirth())
                .dateRegistration(user.getDateRegistration())
                .tel(user.getTel())
                .token(jwtUtils.generateJwtToken(userDetails)) // Генерируем JWT токен.
                .refreshToken(refreshToken.getToken()) // Добавляем refresh токен в ответ.
                .build());
    }

    public ResponseEntity<?> register(CreateUserRequest createUserRequest) {
        String tel = phoneNumberService.valid(createUserRequest.getTel());

        if (tel == null) return ResponseEntity.status(400).body("Невалидный номер телефона!");
        if (userRepository.existsByTel(tel))
            return ResponseEntity.status(400).body("Данный номер уже зарегистрирован!");

        createUserRequest.setRoles(Collections.singleton(RoleType.ROLE_USER));

        User user = User.builder()
                .tel(tel)
                .name(createUserRequest.getName())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .roles(createUserRequest.getRoles())
                .build();

        userRepository.save(user);
        return authenticateUser(new LoginRequest(createUserRequest.getTel(), createUserRequest.getPassword()));
    }

    public ResponseEntity<?> refreshTokenResponse(RefreshTokenRequest refreshTokenRequest) {
        // Получаем refresh токен из запроса.
        RefreshToken refreshToken = refreshTokenService.findByRefreshToken(refreshTokenRequest.getRefreshToken()).orElse(null);
        if (refreshToken != null) {
            Long userId = refreshTokenService.findByRefreshToken(refreshToken.getToken())
                    .map(refreshTokenService::checkRefreshToken) // Проверяем действительность refresh токена.
                    .map(RefreshToken::getUserId).orElse(null);
            if (userId != null) {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    String token = jwtUtils.generateTokenFromUserTel(user.getTel());
                    return ResponseEntity.status(200).body(new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken()));
                } else {
                    return ResponseEntity.status(404).body("Пользователь не найден!");
                }
            }

        }
        return ResponseEntity.status(404).body("RefreshToken не найден");
    }

    public ResponseEntity<SimpleResponse> logout(UserDetails userDetails) {
        if (userDetails != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof AppUserDetails appUserDetails) {
                Long userId = appUserDetails.getId(); // Получаем ID пользователя из контекста.
                refreshTokenService.deleteByUserId(userId); // Удаляем все refresh токены, связанные с пользователем.
            }
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(new SimpleResponse("Пользователь успешно вышел."));
        }
        return ResponseEntity.ok(new SimpleResponse("Пользователь не найден, но выход всё равно совершен"));

    }
}
