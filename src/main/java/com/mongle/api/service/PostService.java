package com.mongle.api.service;

import jakarta.servlet.http.HttpServletRequest;

import com.mongle.api.domain.Post;
import com.mongle.api.dto.post.PostRequestDto;

public interface PostService {
    Post createPost(PostRequestDto.CreateDto request, HttpServletRequest authorizationHeader);
    Post updatePost(PostRequestDto.UpdateDto request, Integer postId, String authorizationHeader);
}
