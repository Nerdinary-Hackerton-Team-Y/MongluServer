package com.mongle.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongle.api.controller.PostController;
import com.mongle.api.domain.Hashtag;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.User;
import com.mongle.api.domain.enums.Order;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.domain.mapping.PostHashtag;
import com.mongle.api.dto.post.PostRequestDto;
import com.mongle.api.dto.post.PostResponseDto;
import com.mongle.api.exception.GeneralException;
import com.mongle.api.exception.handler.PostHandler;
import com.mongle.api.repository.HashtagRepository;
import com.mongle.api.repository.PostHashtagRepository;
import com.mongle.api.repository.PostRepository;
import com.mongle.api.repository.QuestRepository;
import com.mongle.api.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;
    private final QuestRepository questRepository;


    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsOfUser(User user, Order order) {
        List<Post> posts;

        if (order == Order.Date) {
            posts = postRepository.findAllByOrderByCreatedAt();
        } else {
            posts = postRepository.findAllByOrderByScoreDesc();
        }

        return posts.stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .map(PostResponseDto::of)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(List<String> hashtag, Order order, Boolean isquest) {
        List<Post> posts;

        if (order == Order.Date) {
            posts = postRepository.findAllByOrderByCreatedAt();
        } else {
            posts = postRepository.findAllByOrderByScoreDesc();
        }

        if (hashtag.size() > 0) {
            posts = posts.stream()
                    .filter(p -> {
                        return p.getPostHashtagList().stream().anyMatch(ph -> ph.getHashtag().getTag().equals(hashtag.get(0)));
                    })
                    .toList();
        }

        if (isquest != null) {
            posts = posts.stream()
                    .filter(p -> {
                return p.getIsQuest().equals(isquest);
                })
                .toList();
        }

        return posts.stream()
                .map(PostResponseDto::of)
                .toList();
    }

    @Override
    @Transactional
    public Post createPost(PostRequestDto.CreateDto request, User user) {
        // Validate quest_id
        Quest quest = questRepository.findById(request.getQuestId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.QUEST_NOT_FOUND));

        Post newPost = PostController.toPost(request);
        newPost.setUser(user); // Set the user
        newPost.setQuest(quest); // Set the quest
        newPost.setStatus(Status.ACTIVATED);

        List<Hashtag> hashtags = request.getHashtags().stream()
                .map(this::getOrCreateHashtag)
                .collect(Collectors.toList());

        hashtags.forEach(hashtag -> {
            PostHashtag postHashtag = PostHashtag.builder()
                    .post(newPost)
                    .hashtag(hashtag)

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
    public Post updatePost(PostRequestDto.UpdateDto request, Integer postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(String.valueOf(request.getImageUrl()));

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post deletePost(Integer postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));

        post.setStatus(Status.DEACTIVATED); // Set the status to 'DELETED'

        return postRepository.save(post);
    }

    public Post findById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

}
