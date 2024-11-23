package com.mongle.api.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import com.mongle.api.service.S3Service;
import com.mongle.api.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.User;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthServiceImpl;
import com.mongle.api.service.PostService;
import com.mongle.api.response.code.status.ErrorStatus;
import com.mongle.api.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final AuthServiceImpl authServiceImpl;
    private final S3Service s3Service;

    @PostMapping("/")
    public ApiResponse<PostResponseDto.CreateResultDto> createPost(HttpServletRequest request, @RequestBody @Valid PostRequestDto.CreateDto postRequest) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        String imageUrl = null;

        if (postRequest.getImageUrl() != null) {
            try {
                imageUrl = s3Service.uploadFile("post-images", postRequest.getImageUrl());
            } catch (IOException e) {
                return ApiResponse.onFailure("FILE4001", "파일 업로드에 실패했습니다.", null);
            }
        }

        Post post = postService.createPost(postRequest, user, imageUrl);
        return ApiResponse.onSuccess(toCreateResultDto(post));
    }

    public static PostResponseDto.CreateResultDto toCreateResultDto(Post post) {
        return PostResponseDto.CreateResultDto.builder()
                .postId(post.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Post toPost(PostRequestDto.CreateDto request) {
        Quest quest = Quest.builder().id(request.getQuestId()).build();
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(String.valueOf(request.getImageUrl()))
                .isQuest(request.getIsQuest())
                .quest(quest)
                .build();
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponseDto.UpdateResultDto> updatePost(
            HttpServletRequest request,
            @RequestBody @Valid PostRequestDto.UpdateDto postRequest,
            @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        Post post = postService.updatePost(postRequest, postId, user);
        return ApiResponse.onSuccess(toUpdateResultDto(post));
    }

    public static PostResponseDto.UpdateResultDto toUpdateResultDto(Post post) {
        return PostResponseDto.UpdateResultDto.builder()
                .postId(post.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Post toPost(PostRequestDto.UpdateDto request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(String.valueOf(request.getImageUrl()))
                .build();
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.DeleteResultDto> deletePost(
            HttpServletRequest request,
            @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        Post post = postService.deletePost(postId, user);
        return ApiResponse.onSuccess(toDeleteResultDto(post));
    }

    public static PostResponseDto.DeleteResultDto toDeleteResultDto(Post post) {
        return PostResponseDto.DeleteResultDto.builder()
                .postId(post.getId())
                .status(post.getStatus())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}