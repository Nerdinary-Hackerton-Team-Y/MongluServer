package com.mongle.api.domain;

import com.mongle.api.domain.common.BaseEntity;
import com.mongle.api.domain.mapping.PostHashtag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String imageUrl;

    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isQuest;

    @Column(columnDefinition = "INTEGER")
    private Integer rank;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    // 양방향 매핑
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostHashtag> postHashtagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questId")
    private Quest quest;
}
