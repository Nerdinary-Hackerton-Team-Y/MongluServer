package com.mongle.api.service;

import java.util.List;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Order;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;

public interface PostService {

    List<PostResponseDto> getPosts(List<String> hashtag, Order order);
    List<PostResponseDto> getPostsOfUser(User user, Order order);
    Post createPost(PostRequestDto.CreateDto request, User user);
    Post updatePost(PostRequestDto.UpdateDto request, Integer postId, User user);
    Post deletePost(Integer postId, User user);

    Post findById(Integer postId);
}
