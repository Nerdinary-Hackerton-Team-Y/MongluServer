package com.mongle.api.controller;

import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/image")
public class PostImageController {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadPostImage(
            @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = s3Service.uploadFile("post", file);

        return ResponseEntity.ok(ApiResponse.onSuccess(fileUrl));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deletePostImage(
            @RequestParam("fileUrl") String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Service.deleteFile("post", fileName);

        return ResponseEntity.ok(ApiResponse.onSuccess("포스트 이미지 삭제 성공"));
    }
}
