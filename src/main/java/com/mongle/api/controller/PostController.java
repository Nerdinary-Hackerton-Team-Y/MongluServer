package com.mongle.api.controller;

import com.mongle.api.domain.Post;
import com.mongle.api.dto.post.PostRequestDTO;
import com.mongle.api.dto.post.PostResponseDTO;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public ApiResponse<PostResponseDTO.CreateResultDTO> createPost(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid PostRequestDTO.CreateDTO request) {
        Post post = postService.createPost(request, authorizationHeader);
        return ApiResponse.onSuccess(toCreateResultDTO(post));
    }

    public static PostResponseDTO.CreateResultDTO toCreateResultDTO(Post post) {
        return PostResponseDTO.CreateResultDTO.builder()
                .postId(post.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Post toPost(PostRequestDTO.CreateDTO request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .isQuest(request.getIsQuest())
                .questId(request.getQuestId())
                .build();
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponseDTO.UpdateResultDTO> updatePost(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid PostRequestDTO.UpdateDTO request,
            @PathVariable Integer postId) {
        Post post = postService.updatePost(request, postId, authorizationHeader);
        return ApiResponse.onSuccess(toUpdateResultDTO(post));
    }

    public static PostResponseDTO.UpdateResultDTO toUpdateResultDTO(Post post) {
        return PostResponseDTO.UpdateResultDTO.builder()
                .postId(post.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Post toPost(PostRequestDTO.UpdateDTO request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .build();
    }
}