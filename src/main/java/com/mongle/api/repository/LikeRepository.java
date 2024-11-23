package com.mongle.api.repository;

import com.mongle.api.domain.LikeHistory;
import com.mongle.api.domain.Post;
import com.mongle.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeHistory, Long> {
    LikeHistory findByUserAndPost(User user, Post post);
    Integer countByPost(Post post);
    LikeHistory findByPost(Post post);
}
