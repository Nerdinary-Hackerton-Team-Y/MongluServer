package com.mongle.api.controller;

import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = s3Service.uploadFile(folder, file);
        return ResponseEntity.ok(ApiResponse.onSuccess(fileUrl));
    }
}
