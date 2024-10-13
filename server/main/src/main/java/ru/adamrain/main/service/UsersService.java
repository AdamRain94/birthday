package ru.adamrain.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
