package com.example.springwithhibernate.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final Logger logger = LogManager.getLogger();
    private final Path rootLocation = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + filename);
        }
    }

    public String saveFile(MultipartFile file, String prefix) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new RuntimeException("File name is null");
            }

            Path directory = rootLocation.resolve(prefix);
            Files.createDirectories(directory);
            try {
                Files.copy(file.getInputStream(), directory.resolve(originalFilename));
            } catch (Exception e) {}
            return Paths.get(prefix).resolve(originalFilename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
