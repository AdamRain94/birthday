package ru.adamrain.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.service.UserService;

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
