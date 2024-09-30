package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.service.UserService;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class UserSettingController {


    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        System.out.println("===========================");
        return ResponseEntity.ok("ok");
    }
}
