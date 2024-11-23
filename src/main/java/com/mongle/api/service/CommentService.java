package com.mongle.api.service;

import com.mongle.api.domain.Comment;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.dto.CommentReqDto;
import com.mongle.api.domain.dto.CommentResDto;
import com.mongle.api.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentResDto createComment(Integer postId, Integer userId, CommentReqDto commentReqDto) {
        Post post = postRepository.findById(postId);
        User user = userService.findById(userId);

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(commentReqDto.getContent())
                .build();

        commentRepository.save(comment);

        return new CommentResDto(comment);
    }

}
