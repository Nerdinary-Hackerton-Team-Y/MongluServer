package com.mongle.api.controller;

import com.mongle.api.domain.User;
import com.mongle.api.dto.like.LikeResponseDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.AuthServiceImpl;
import com.mongle.api.service.LikeService;
import com.mongle.api.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {
    private final LikeService likeService;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/")
    public ApiResponse<LikeResponseDto.CreateResultDto> createLike(HttpServletRequest request, @PathVariable Integer postId) {
        User user = AuthUtil.getUserFromRequest(request, authServiceImpl);
        LikeResponseDto.CreateResultDto result = likeService.createLike(postId, user.getId());
        return ApiResponse.onSuccess(result);
    }
}