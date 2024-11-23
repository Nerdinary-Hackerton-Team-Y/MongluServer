package com.mongle.api.service;

import com.mongle.api.domain.LikeHistory;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.dto.like.LikeResponseDto;
import com.mongle.api.repository.LikeRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public LikeResponseDto.CreateResultDto createLike(Integer postId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        LikeHistory like = LikeHistory.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);

        return LikeResponseDto.CreateResultDto.builder()
                .likeId(like.getId())
                .PostId(post.getId())
                .status(Status.ACTIVATED)
                .createdAt(like.getCreatedAt())
                .build();
    }
}