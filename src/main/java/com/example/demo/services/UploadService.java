package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface UploadService {
    String save(MultipartFile file, String directory) throws IOException;

    String save(MultipartFile file, String directory, String fileNameWithoutExtension)
            throws IOException;
}
