package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {


    private final UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = usersService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }
}
