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
        // generate unique file name
        String fileName = UUID.randomUUID().toString();
        return  save(file, directory, fileName);
    }

    @Override
    public String save(MultipartFile file, String directory, String fileNameWithoutExtension)
            throws IOException {
        // get fileExtension
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = fileNameWithoutExtension + fileExtension;

        // check directory exists
        Path directoryPath = Paths.get(ROOT_DIRECTORY, directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        // create path
        Path filePath = Paths.get(directoryPath.toString(), fileName);
        // save to local file system
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // return new name
        return fileName;
    }
}
