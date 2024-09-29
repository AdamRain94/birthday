package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.entity.RoleType;
import ru.adamrain.main.exception.AlreadyExistException;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.security.SecurityService;
import ru.adamrain.main.web.model.*;

import java.util.Collections;

// Контроллер для управления аутентификацией и регистрацией пользователей.
@RestController
@RequestMapping("/api/auth") // Общий префикс для всех маршрутов этого контроллера
@RequiredArgsConstructor // Генерирует конструктор с обязательными аргументами
public class AuthController {

    private final UserRepository userRepository; // Репозиторий для работы с данными пользователей

    private final SecurityService securityService; // Сервис для управления аутентификацией и авторизацией

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        System.out.println("logout");
        return ResponseEntity.ok("logout");
    }

    // Метод для аутентификации пользователя по логину и паролю
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) throws InterruptedException {
        // Вызываем сервис для аутентификации и возвращаем результат в ответе
        Thread.sleep(1000);
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    // Метод для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody CreateUserRequest loginRequest) {
        // Проверяем, существует ли уже пользователь с таким номером телефона
        if(userRepository.existsByTel(loginRequest.getTel())){
            throw new AlreadyExistException("Данный номер уже зарегистрирован!");
        }
        // проставляем по умолчанию роль юзера
        loginRequest.setRoles(Collections.singleton(RoleType.ROLE_USER));
        // Регистрируем пользователя через сервис
        securityService.register(loginRequest);
        // Авторизация нового пользователя
        LoginRequest login = new LoginRequest(loginRequest.getTel(), loginRequest.getPassword());
        // Возвращаем пользователя
        return ResponseEntity.ok(securityService.authenticateUser(login));
    }

    // Метод для обновления токена доступа по refresh-токену
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        // Вызываем сервис для обновления токена и возвращаем результат в ответе
        return ResponseEntity.ok(securityService.refreshTokenResponse(request));
    }

    // Метод для выхода пользователя из системы
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        // Вызываем сервис для выхода пользователя
        securityService.logout();
        // Возвращаем сообщение об успешном выходе пользователя
        return ResponseEntity.ok(new SimpleResponse("User logout. PhoneNumber is: " + userDetails.getUsername()));
    }
}
