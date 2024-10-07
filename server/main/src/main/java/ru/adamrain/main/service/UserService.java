package ru.adamrain.main.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.entity.UserPhoto;
import ru.adamrain.main.repository.UserRepository;

import java.io.File;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FileService fileService;

    private final PasswordEncoder passwordEncoder;

    public User getUser(UserDetails userDetails) {
        return userRepository.findByTel(userDetails.getUsername()).orElse(null);
    }

    public User saveUser(UserDetails userDetails, User user) {
        User updateUser = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        if (updateUser == null) return null;
        updateUser.setFam(user.getFam());
        updateUser.setName(user.getName());
        updateUser.setOtch(user.getOtch());
        updateUser.setDateOfBirth(user.getDateOfBirth());
        updateUser.setTel(user.getTel());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(updateUser);
    }

    public User savePhoto(UserDetails userDetails, MultipartFile photo) {
        User user = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        if (user == null) return null;
        String phat = fileService.saveUserPhoto(photo);
        if (phat == null) return null;
        UserPhoto userPhoto = user.getPhoto();
        if (userPhoto == null) userPhoto = new UserPhoto();
        userPhoto.setUser(user);
        userPhoto.setPath(phat);
        user.setPhoto(userPhoto);

        return userRepository.save(user);
    }

    public File getPhoto(UserDetails userDetails) {
        User user = userRepository.findByTel(userDetails.getUsername()).orElse(null);
        if (user == null) return null;
        UserPhoto photo = user.getPhoto();
        if (photo == null) return null;
        String path = photo.getPath();

        return fileService.getUserPhoto(path);
    }

}
