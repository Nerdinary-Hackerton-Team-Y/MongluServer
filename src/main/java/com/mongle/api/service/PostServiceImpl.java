package com.mongle.api.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mongle.api.controller.PostController;
import com.mongle.api.domain.Hashtag;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.domain.mapping.PostHashtag;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.exception.handler.PostHandler;
import com.mongle.api.repository.HashtagRepository;
import com.mongle.api.repository.PostHashtagRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.response.code.status.ErrorStatus;
import com.mongle.api.util.AuthUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthServiceImpl authServiceImpl;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    @Transactional
    public Post createPost(PostRequestDto.CreateDto request, HttpServletRequest authorizationHeader) {
        Post newPost = PostController.toPost(request);
        User user = AuthUtil.getUserFromRequest(authorizationHeader, authServiceImpl);
        newPost.setUser(user); // Set the user

        List<Hashtag> hashtags = request.getHashtags().stream()
                .map(this::getOrCreateHashtag)
                .collect(Collectors.toList());

        hashtags.forEach(hashtag -> {
            PostHashtag postHashtag = PostHashtag.builder()
                    .post(newPost)
                    .hashtag(hashtag)
                    .status(Status.ACTIVATED) // Set status to 'ACTIVATED'
                    .build();
            postHashtagRepository.save(postHashtag);
        });

        return postRepository.save(newPost);
    }

    private Hashtag getOrCreateHashtag(String hashtagName) {
        return hashtagRepository.findByTag(hashtagName)
                .orElseGet(() -> {
                    Hashtag newHashtag = new Hashtag();
                    newHashtag.setTag(hashtagName);
                    return hashtagRepository.save(newHashtag);
                });
    }

    @Override
    @Transactional
    public Post updatePost(PostRequestDto.UpdateDto request, Integer postId, String authorizationHeader) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());

        return postRepository.save(post);
    }
}
