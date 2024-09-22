package ru.adamrain.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.services.UserService;

@RestController
@RequestMapping("/api/setting")
public class UserSettingController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        System.out.println("===========================");
        return ResponseEntity.ok("ok");
    }
}
