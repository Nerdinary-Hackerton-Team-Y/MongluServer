package com.mongle.api.service;

import com.mongle.api.dto.like.LikeResponseDto;

public interface LikeService {
    LikeResponseDto.CreateResultDto createLike(Integer postId, Integer userId);
}