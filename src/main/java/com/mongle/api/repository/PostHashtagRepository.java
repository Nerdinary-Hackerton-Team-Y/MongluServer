package com.mongle.api.repository;

import com.mongle.api.domain.mapping.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Integer> {
}