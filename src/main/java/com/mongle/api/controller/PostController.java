package com.mongle.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Order;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthService;
import com.mongle.api.service.PostService;
import com.mongle.api.util.AuthUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    @GetMapping
    public List<PostResponseDto> getPosts(
        @RequestParam(name = "order") int order,
        @RequestParam(name = "hashtag", required = false) String hashtag,
        @RequestParam(name = "isquest", required = false) Boolean isquest
    ) {
        List<String> hashtagList;
        if (hashtag == null)
            hashtagList = List.of();
        else
            hashtagList = List.of(hashtag);

        if (order == 0)
            return postService.getPosts(hashtagList, Order.Date, isquest);
        else
            return postService.getPosts(hashtagList, Order.Score, isquest);
    }

    @GetMapping("/me")
    public List<PostResponseDto> getMyPosts(
        @RequestParam(name = "order") int order,
        HttpServletRequest request
    ) {
        User user = AuthUtil.getUserFromRequest(request, authService);

        if (order == 0)
            return postService.getPostsOfUser(user, Order.Date);
        else
            return postService.getPostsOfUser(user, Order.Score);
    }

    @PostMapping("/")
    public ApiResponse<PostResponseDto.CreateResultDto> createPost(HttpServletRequest request, @RequestBody @Valid PostRequestDto.CreateDto postRequest) {
        User user = AuthUtil.getUserFromRequest(request, authService);
        Post post = postService.createPost(postRequest, user);
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
        User user = AuthUtil.getUserFromRequest(request, authService);
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
                .imageUrl(request.getImageUrl())
                .build();
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto.DeleteResultDto> deletePost(
            HttpServletRequest request,
            @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authService);
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
