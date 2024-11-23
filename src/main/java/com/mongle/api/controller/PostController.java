package com.mongle.api.controller;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.User;
import com.mongle.api.dto.post.PostRequestDTO;
import com.mongle.api.dto.post.PostResponseDTO;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthServiceImpl;
import com.mongle.api.service.PostService;
import com.mongle.api.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/")
    public ApiResponse<PostResponseDTO.CreateResultDTO> createPost(HttpServletRequest request, @RequestBody @Valid PostRequestDTO.CreateDTO postRequest) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        Post post = postService.createPost(postRequest, user);
        return ApiResponse.onSuccess(toCreateResultDTO(post));
    }

    public static PostResponseDTO.CreateResultDTO toCreateResultDTO(Post post) {
        return PostResponseDTO.CreateResultDTO.builder()
                .postId(post.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Post toPost(PostRequestDTO.CreateDTO request) {
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
    public ApiResponse<PostResponseDTO.UpdateResultDTO> updatePost(
            HttpServletRequest request,
            @RequestBody @Valid PostRequestDTO.UpdateDTO postRequest,
            @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        Post post = postService.updatePost(postRequest, postId, user);
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