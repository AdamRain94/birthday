package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.service.UserService;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class UserSettingController {


    private final UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {
        Thread.sleep(1000);
        User user = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws InterruptedException {
        Thread.sleep(1000);
        User upadateUser = userRepository.save(user);
        return ResponseEntity.ok(upadateUser);
    }
}
