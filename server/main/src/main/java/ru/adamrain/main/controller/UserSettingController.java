package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.service.UserService;

import java.io.File;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails);
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не найден!");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userService.saveUser(userDetails, user);
    }

    @PostMapping("/photo")
    public ResponseEntity<?> updatePhotoUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("photo") MultipartFile photo) {
        User user = userService.savePhoto(userDetails, photo);
        if (user == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при сохранении фото!");
        return ResponseEntity.ok(user);
    }
}
