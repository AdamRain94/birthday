package ru.adamrain.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws InterruptedException {
        Thread.sleep(1000);
        User user = userService.authorization(request.getTel(), request.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль!");
        }
    }

    @PostMapping("/logout")
    public void logout() {
        System.out.println("logout");
    }
}
