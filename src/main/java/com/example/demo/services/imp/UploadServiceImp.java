package com.example.demo.services.imp;

import com.example.demo.services.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadServiceImp implements UploadService {

    @Value("${upload.path}")
    private String ROOT_DIRECTORY;

    @Override
    public String save(MultipartFile file, String directory)
            throws IOException {
        // chech directory exists
        Path directoryPath = Paths.get(ROOT_DIRECTORY, directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        // generate unique file name
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + fileExtension;
        // create path
        Path filePath = Paths.get(directoryPath.toString(), newFileName);
        // save to local file system
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return new name
        return newFileName;
    }
}
