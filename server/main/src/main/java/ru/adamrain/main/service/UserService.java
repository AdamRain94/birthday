package ru.adamrain.main.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FileService fileService;

    private final PasswordEncoder passwordEncoder;

    private final PhoneNumberService phoneNumberService;

    public User getUser(UserDetails userDetails) {
        return userRepository.findByTel(userDetails.getUsername()).orElse(null);
    }

    public ResponseEntity<?> saveUser(UserDetails userDetails, User user) {
        User updateUser = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        if (updateUser == null)
            return ResponseEntity.status(400).body("Произошла ошибка при сохранении пользователя!");
        String tel = phoneNumberService.valid(user.getTel());
        if (tel == null)
            return ResponseEntity.status(400).body("Невалидный номер телефона!");
        if (userRepository.existsByTel(tel) && !tel.equals(updateUser.getTel()))
            return ResponseEntity.status(400).body("Данный номер уже зарегистрирован!");
        updateUser.setFam(user.getFam());
        updateUser.setName(user.getName());
        updateUser.setOtch(user.getOtch());
        updateUser.setSex(user.getSex());
        updateUser.setDateOfBirth(user.getDateOfBirth());
        updateUser.setTel(tel);
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(updateUser);
        return ResponseEntity.ok(saveUser);
    }

    public User savePhoto(UserDetails userDetails, MultipartFile photo) {
        User user = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        if (user == null) return null;
        String fileName = fileService.saveUserPhoto(photo, user.getTel());
        if (fileName == null) return null;
        if (user.getPhoto() != null) fileService.deleteUserPhoto(user.getPhoto());
        user.setPhoto(fileName);
        return userRepository.save(user);
    }


}
