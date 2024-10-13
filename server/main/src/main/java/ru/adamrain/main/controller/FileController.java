package ru.adamrain.main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {


    @Value("${app.files.dir.images.user-photo}")
    private String userPhotoDir;

    @GetMapping("/photo/user/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(System.getProperty("user.dir")).resolve(userPhotoDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Файл не найден или не может быть прочитан");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении файла", e);
        }
    }
}
