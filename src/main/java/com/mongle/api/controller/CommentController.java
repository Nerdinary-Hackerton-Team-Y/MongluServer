package com.mongle.api.controller;

import com.mongle.api.domain.User;
import com.mongle.api.domain.dto.CommentReqDto;
import com.mongle.api.domain.dto.CommentResDto;
import com.mongle.api.response.ApiResponse;
import com.mongle.api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final AuthController authController;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<?> createComment(@RequestParam(name = "postId", required = true) Integer postId,
                                           @RequestBody CommentReqDto commentReqDto,
                                           HttpServletRequest request) {
        User user = authController.getUserInfo(request);
        CommentResDto commentResDto = commentService.createComment(postId, user.getId(), commentReqDto);

        return ResponseEntity.ok(ApiResponse.onSuccess(commentResDto));
    }

}
