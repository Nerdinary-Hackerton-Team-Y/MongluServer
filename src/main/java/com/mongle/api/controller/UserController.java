package com.mongle.api.controller;

import com.mongle.api.domain.User;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.S3Service;
import com.mongle.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;
    private final AuthController authController;

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse> uploadProfileImage(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        User user = authController.getUserInfo(request);

        if (user.getProfilePictureUrl() != null) {
            String fileName = user.getProfilePictureUrl().substring(user.getProfilePictureUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile("profile", fileName);
        }

        String fileUrl = s3Service.uploadFile("profile", file);
        userService.updateProfileImage(user, fileUrl);

        return ResponseEntity.ok(ApiResponse.onSuccess(fileUrl));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<ApiResponse> uploadProfileImage(HttpServletRequest request) {
        User user = authController.getUserInfo(request);

        if (user.getProfilePictureUrl() != null) {
            String fileName = user.getProfilePictureUrl().substring(user.getProfilePictureUrl().lastIndexOf("/") + 1);
            s3Service.deleteFile("profile", fileName);
            userService.deleteProfileImage(user);
        }

        return ResponseEntity.ok(ApiResponse.onSuccess("프로필 사진 삭제 성공"));
    }
}
