package com.mongle.api.controller;

import com.mongle.api.domain.User;
import com.mongle.api.dto.like.LikeResponseDto;
import com.mongle.api.repository.LikeRepository;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthServiceImpl;
import com.mongle.api.service.LikeService;
import com.mongle.api.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {
    private final LikeService likeService;
    private final AuthServiceImpl authServiceImpl;
    private final LikeRepository likeRepository;

    @PostMapping("/")
    public ApiResponse<LikeResponseDto.CreateResultDto> createLike(HttpServletRequest request, @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        LikeResponseDto.CreateResultDto result = likeService.createLike(postId, user.getId());
        return ApiResponse.onSuccess(result);
    }

    @DeleteMapping("/{likeId}")
    public ApiResponse<LikeResponseDto.DeleteResultDto> deleteLike(HttpServletRequest request, @PathVariable Integer postId, @PathVariable Long likeId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        LikeResponseDto.DeleteResultDto result = likeService.deleteLike(postId, user.getId(), likeId);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/")
    public ApiResponse<LikeResponseDto.GetLikesResultDto> getLikes(@PathVariable Integer postId) {
        LikeResponseDto.GetLikesResultDto result = likeService.getLikes(postId);
        return ApiResponse.onSuccess(result);
    }
}