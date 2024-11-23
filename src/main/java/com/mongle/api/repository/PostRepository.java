package com.mongle.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mongle.api.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByOrderByCreatedAt();
    List<Post> findAllByOrderByScoreDesc();

}
