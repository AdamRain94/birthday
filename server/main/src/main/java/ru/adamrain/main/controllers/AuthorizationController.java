package ru.adamrain.main.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adamrain.main.dto.LoginRequest;
import ru.adamrain.main.entitys.User;
import ru.adamrain.main.services.UserService;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) throws InterruptedException {
        Thread.sleep(1000);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getTel(), loginRequest.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Логируем успешную аутентификацию
            System.out.println("User " + loginRequest.getTel() + " authenticated successfully.");

            User user = userService.authorization(loginRequest.getTel(), loginRequest.getPassword());
            if (user != null) {
                // Получаем идентификатор сессии
                String sessionId = request.getSession().getId();
                // Устанавливаем куки
                Cookie cookie = new Cookie("JSESSIONID", sessionId);
                cookie.setHttpOnly(true);
                cookie.setSecure(true); // Только через HTTPS
                cookie.setPath("/"); // Доступен для всех путей
                cookie.setAttribute("SameSite", "None");// Или "Lax"

                response.addCookie(cookie);

                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль!");
            }
        } catch (Exception e) {
            // Логируем ошибку аутентификации
            System.out.println("Authentication failed for user " + loginRequest.getTel() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль!");
        }
    }


    @PostMapping("/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
        System.out.println("logout");
    }
}
