package com.mongle.api.service;

import com.mongle.api.controller.PostController;
import com.mongle.api.domain.Hashtag;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import com.mongle.api.domain.mapping.PostHashtag;
import com.mongle.api.dto.post.PostRequestDTO;
import com.mongle.api.exception.handler.UserHandler;
import com.mongle.api.repository.HashtagRepository;
import com.mongle.api.repository.PostHashtagRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.response.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthServiceImpl authServiceImpl;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    @Transactional
    public Post createPost(PostRequestDTO.CreateDTO request, String authorizationHeader) {
        String token = authServiceImpl.getTokenFromHeader(authorizationHeader);
        Post newPost = PostController.toPost(request);
//        User user = authServiceImpl.getUserFromToken(token)
//                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        User user = User.builder().id(1).build();

        Hashtag hashtag = getHashtag(request);

        PostHashtag postHashtag = PostHashtag.builder()
                .post(newPost)
                .hashtag(hashtag)
                .build();

        postHashtag.setPost(newPost);
        postHashtag.setHashtag(hashtag);

        postHashtagRepository.save(postHashtag);

        return postRepository.save(newPost);
    }

    private Hashtag getHashtag(PostRequestDTO.CreateDTO request) {
        String hashtagName = request.getHashtagName();

        Hashtag hashtag = hashtagRepository.findByName(hashtagName)
                .orElseGet(() -> {
                    Hashtag newHashtag = new Hashtag();
                    newHashtag.setTag(hashtagName);
                    return hashtagRepository.save(newHashtag);
                });

        return hashtag;
    }
}