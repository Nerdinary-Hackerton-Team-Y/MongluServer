package com.mongle.api.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthServiceImpl;
import com.mongle.api.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/")
    public ApiResponse<PostResponseDto.CreateResultDto> createPost(HttpServletRequest request, @RequestBody @Valid PostRequestDto.CreateDto postRequest) {
        Post post = postService.createPost(postRequest, request);
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
                .imageUrl(request.getImageUrl())
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
        return ApiResponse.onSuccess(toUpdateResultDTO(post));
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
                .imageUrl(request.getImageUrl())
                .build();
    }
}
