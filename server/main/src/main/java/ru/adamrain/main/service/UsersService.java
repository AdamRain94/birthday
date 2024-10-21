package ru.adamrain.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.web.model.UserInListResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;

    public ResponseEntity<Object> getAllUsers() {
        List<UserInListResponse> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            users.add(UserInListResponse.builder()
                    .id(user.getId())
                    .fam(user.getFam())
                    .name(user.getName())
                    .otch(user.getOtch())
                    .tel(user.getTel())
                    .photo(user.getPhoto())
                    .dateOfBirth(user.getDateOfBirth())
                    .build()
            );
        });
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<Object> getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return ResponseEntity.status(404).body("Пользователя с id " +id+" не существует");
        return ResponseEntity.ok(user);
    }
}
