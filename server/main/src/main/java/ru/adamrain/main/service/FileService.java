package ru.adamrain.main.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${app.files.dir.images.user-photo}")
    private String userPhotoDir;

    public String saveUserPhoto(MultipartFile file) {
        return saveFile(file, userPhotoDir);
    }

    public File getUserPhoto(String path) {
        File file = new File(path);
        return file.exists() ? file : null;
    }

    private String saveFile(MultipartFile file, String dir) {
        String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", "");
        Path rootPath = Paths.get(System.getProperty("user.dir")).resolve(dir);
        Path filePath = rootPath.resolve(fileName);

        try {
            Files.createDirectories(rootPath);
        } catch (IOException e) {
            log.error("Не удалось создать директорию! " + e.getMessage());
            return null;
        }

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            log.error("Не удалось сохранить файл! " + e.getMessage());
            return null;
        }

        return filePath.toString();
    }

}
