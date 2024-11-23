package com.mongle.api.service;

import com.mongle.api.domain.Post;
import com.mongle.api.dto.post.PostRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface PostService {
    Post createPost(PostRequestDTO.CreateDTO request, HttpServletRequest authorizationHeader);
    Post updatePost(PostRequestDTO.UpdateDTO request, Integer postId, String authorizationHeader);
}