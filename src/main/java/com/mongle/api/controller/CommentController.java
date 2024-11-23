package com.mongle.api.controller;

import com.mongle.api.domain.Comment;
import com.mongle.api.domain.User;
import com.mongle.api.dto.comment.CommentReqDto;
import com.mongle.api.dto.comment.CommentResDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final AuthController authController;

    @PostMapping
    public ResponseEntity<ApiResponse> createComment(@RequestParam(name = "postId", required = true) Integer postId,
                                           @RequestBody CommentReqDto commentReqDto,
                                           HttpServletRequest request) {
        User user = authController.getUserInfo(request);
        CommentResDto commentResDto = commentService.createComment(postId, user.getId(), commentReqDto);

        return ResponseEntity.ok(ApiResponse.onSuccess(commentResDto));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteComment(@RequestParam(name = "commentId", required = true) Integer commentId,
                              HttpServletRequest request) {
        Comment comment = commentService.findById(commentId);
        User user = authController.getUserInfo(request);
        commentService.deleteComment(commentId, user.getId());

        return ResponseEntity.ok(ApiResponse.onSuccess("댓글 삭제 성공"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getComments(@RequestParam(name = "postId", required = true) Integer postId,
                                                   HttpServletRequest request) {
        List<CommentResDto> commentListResDto = commentService.findByPostId(postId);

        return ResponseEntity.ok(ApiResponse.onSuccess(commentListResDto));
    }

}
