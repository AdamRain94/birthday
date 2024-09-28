package ru.adamrain.main.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public User registerUser(User user) {
//        if (userRepository.findByTel(user.getTel()) != null) {
//            throw new RuntimeException("Номер телефона уже зарегистрирован");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифруем пароль
//        return userRepository.save(user);
//    }
//
//    public User authorization(String tel, String password) {
//        User user = userRepository.findByTel(tel);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) return user;
//        return null;
//    }
}
