package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.entity.RoleType;
import ru.adamrain.main.exception.AlreadyExistException;
import ru.adamrain.main.exception.UserTelNotFoundExcepion;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.security.SecurityService;
import ru.adamrain.main.service.PhoneNumberService;
import ru.adamrain.main.web.model.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // Генерирует конструктор с обязательными аргументами
public class AuthController {

    private final SecurityService securityService;

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails) {
        return securityService.logout(userDetails);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authUser(@RequestBody LoginRequest loginRequest) throws InterruptedException {
        Thread.sleep(1000);
        return securityService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody CreateUserRequest createUserRequest) throws InterruptedException {
        Thread.sleep(1000);
        return securityService.register(createUserRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest request) {
        return securityService.refreshTokenResponse(request);
    }

}
