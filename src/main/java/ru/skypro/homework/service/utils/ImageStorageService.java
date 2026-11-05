package ru.skypro.homework.service.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageService {
    @Value("${app.upload.dir.ads}")
    private String uploadDirAds;

    @Value("${app.upload.dir.avatar}")
    private String uploadDirAvatar;

    public String save(MultipartFile file, String source) throws IOException {
        if (!file.isEmpty()) {
            String uploadDir;
            if (Objects.equals(source, "AVATAR")) {
                uploadDir = uploadDirAvatar;
            } else {
                uploadDir = uploadDirAds;
            }

            // Генерируем уникальное имя файла, чтобы избежать перезаписи старых изображений.
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueName = UUID.randomUUID() + extension;

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs(); // Проверка результата создания директории!
                if (!created) throw new IOException("Не удалось создать директорию " + uploadDir);
            }

            Files.copy(file.getInputStream(), new File(dir, uniqueName).toPath());
            return "/" + uniqueName; // Возвращаем только имя файла без полного пути
        } else {
            throw new IllegalArgumentException("Файл изображения пуст");
        }
    }
}
