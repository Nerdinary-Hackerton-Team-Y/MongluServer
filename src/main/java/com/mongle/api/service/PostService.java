package com.mongle.api.service;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.dto.post.PostRequestDto;

public interface PostService {
    Post createPost(PostRequestDto.CreateDto request, User user);
    Post updatePost(PostRequestDto.UpdateDto request, Integer postId, User user);
    Post deletePost(Integer postId, User user);
}