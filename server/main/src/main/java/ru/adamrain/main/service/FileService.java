package ru.adamrain.main.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${app.files.dir.images.user-photo}")
    private String userPhotoDir;

    public String saveUserPhoto(MultipartFile file, String tel) {
        return saveFile(file, userPhotoDir, tel);
    }

    public File getUserPhoto(String path) {
        File file = new File(path);
        return file.exists() ? file : null;
    }

    private String saveFile(MultipartFile file, String dir, String tel) {
        String fileName = UUID.randomUUID() + "_" + formatTel(tel) + ".jpg";
        Path rootPath = Paths.get(System.getProperty("user.dir")).resolve(dir);
        Path filePath = rootPath.resolve(fileName);

        try {
            Files.createDirectories(rootPath);
        } catch (IOException e) {
            log.error("Не удалось создать директорию! " + e.getMessage());
            return null;
        }

        try {
            File tempFile = new File(filePath.toString());
            file.transferTo(tempFile);
            int orientation = getOrientation(tempFile);
            BufferedImage resizedImage = Thumbnails.of(tempFile)
                    .size(500, 500) // Размеры
                    .outputFormat("jpg")
                    .crop(net.coobird.thumbnailator.geometry.Positions.CENTER) // Обрезка по центру
                    .asBufferedImage();

            if (orientation == 6) {
                resizedImage = rotateImage(resizedImage, 90);
            } else if (orientation == 3) {
                resizedImage = rotateImage(resizedImage, 180);
            } else if (orientation == 8) {
                resizedImage = rotateImage(resizedImage, 270);
            }

            Thumbnails.of(resizedImage)
                    .size(500, 500)
                    .toFile(tempFile);

        } catch (IOException e) {
            log.error("Не удалось сохранить файл! " + e.getMessage());
            return null;
        }

        return filePath.toString();
    }

    private int getOrientation(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                return directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 1;
    }

    private BufferedImage rotateImage(BufferedImage image, int angle) {
        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        java.awt.Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angle), (double) image.getWidth() / 2, (double) image.getHeight() / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

    public void deleteUserPhoto(String path) {
        File file = new File(path);
        boolean delete = false;
        if (file.exists()) {
            delete = file.delete();
        }
        log.info(delete ? "Файл \"" + path + "\" удалён" : "Не удалось удалить файл \"" + path+"\"");
    }

    private String formatTel(String tel){
        return tel.replaceAll("-", "")
                .replaceAll(" ", "")
                .replaceAll("\\+", "");
    }
}
