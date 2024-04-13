package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class ExcelController {

    private static final String UPLOAD_DIR = "./uploads";

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }
        try {
            byte[] bytes = file.getBytes();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File excelFile = new File(uploadDir.getAbsolutePath() + "/" + file.getOriginalFilename());
            try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
                outputStream.write(bytes);
            }
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}
