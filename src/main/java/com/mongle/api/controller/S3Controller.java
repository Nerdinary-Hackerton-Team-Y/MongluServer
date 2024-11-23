package com.mongle.api.controller;

import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.S3Service;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AuthController authController;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("folder") String folder,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        authController.getUserInfo(request);
        String fileUrl = s3Service.uploadFile(folder, file);

        return ResponseEntity.ok(ApiResponse.onSuccess(fileUrl));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteFile(
            @RequestParam("folder") String folder,
            @RequestParam("fileName") String fileName,
            HttpServletRequest request) {
        authController.getUserInfo(request);
        s3Service.deleteFile(folder, fileName);

        return ResponseEntity.ok(ApiResponse.onSuccess("파일 삭제 성공"));
    }

}
