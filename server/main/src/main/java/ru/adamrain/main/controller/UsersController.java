package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adamrain.main.service.UsersService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {


    private final UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}") // Используем аннотацию для извлечения id из URL
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        return usersService.getUser(id);
    }
}
