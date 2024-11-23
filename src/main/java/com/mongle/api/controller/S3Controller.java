package com.mongle.api.controller;

import com.mongle.api.response.ApiResponse;
import com.mongle.api.response.code.status.SuccessStatus;
import com.mongle.api.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private S3Service s3Service;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("folder") String folder,
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = s3Service.uploadFile(folder, fileName, file);
        return ResponseEntity.ok(ApiResponse.onSuccess(fileUrl));
    }
}
