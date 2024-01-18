package com.example.demo.services.imp;

import com.example.demo.services.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadServiceImp implements UploadService {

    private static final String ROOT_DIRECTORY = "D:\\self_study\\SpringBoot_\\demo\\src\\main\\resources\\static";
    @Override
    public String save(MultipartFile file, String directory) throws IOException {
        //
        Path directoryPath = Paths.get(ROOT_DIRECTORY, directory);

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path filePath = Paths.get(directoryPath.toString(), file.getOriginalFilename());
        file.transferTo(filePath);

        return filePath.toString();
    }
}
