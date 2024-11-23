package com.mongle.api.service;

import com.mongle.api.domain.Post;
import com.mongle.api.dto.post.PostRequestDTO;

public interface PostService {
    Post createPost(PostRequestDTO.CreateDTO request, String authorizationHeader);
    Post updatePost(PostRequestDTO.UpdateDTO request, Integer postId, String authorizationHeader);
}