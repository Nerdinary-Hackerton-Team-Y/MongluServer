package com.mongle.api.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mongle.api.domain.Comment;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.dto.comment.CommentReqDto;
import com.mongle.api.dto.comment.CommentResDto;
import com.mongle.api.exception.GeneralException;
import com.mongle.api.repository.CommentRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository PostRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentResDto createComment(Integer postId, Integer userId, CommentReqDto commentReqDto) {
        Post post = PostRepository.findById(postId)
                .orElseThrow();
        User user = userService.findById(userId);

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(commentReqDto.getContent())
                .build();

        commentRepository.save(comment);

        return new CommentResDto(comment);
    }

    public Comment findById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
    }

    public List<CommentResDto> findByPostId(Integer postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(CommentResDto::new)
                .toList();
    }

    public void deleteComment(Integer commentId, Integer userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
        User user = userService.findById(userId);

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

    public List<CommentResDto> findByUserId(Integer userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);

        return comments.stream()
                .map(CommentResDto::new)
                .toList();
    }
}
