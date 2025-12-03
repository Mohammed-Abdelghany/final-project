package com.example.ecommerce.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorageHelper {

    @Value("${upload.dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String contentType = file.getContentType();
        if (!List.of("image/png", "image/jpg", "image/jpeg").contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds limit");
        }
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFileName;
        Path targetPath = Paths.get(uploadDir).resolve(fileName).normalize();
        if (!targetPath.startsWith(Paths.get(uploadDir))) {
            throw new SecurityException("Invalid file path");
        }
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
