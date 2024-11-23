package com.mongle.api.service;

import com.mongle.api.domain.LikeHistory;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.dto.like.LikeResponseDto;
import com.mongle.api.exception.handler.PostHandler;
import com.mongle.api.exception.handler.UserHandler;
import com.mongle.api.repository.LikeRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.repository.UserRepository;
import com.mongle.api.response.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                .orElseThrow(() -> new UserHandler(ErrorStatus.POST_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

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

    @Override
    @Transactional
    public LikeResponseDto.DeleteResultDto deleteLike(Integer postId, Integer userId, Long likeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        LikeHistory like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        if (!like.getUser().getId().equals(userId) || !like.getPost().getId().equals(postId)) {
            throw new UserHandler(ErrorStatus._UNAUTHORIZED);
        }

        likeRepository.delete(like);

        return LikeResponseDto.DeleteResultDto.builder()
                .likeId(like.getId())
                .PostId(post.getId())
                .status(Status.DEACTIVATED)
                .deleteddAt(LocalDateTime.now())
                .build();
    }
}