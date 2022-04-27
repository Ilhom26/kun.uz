package com.company.entity;

import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "like_table")
@Getter
@Setter
public class LikeEntity extends BaseEntity{
    @Column()
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id",nullable = false)
    private Integer profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;
    @Column(name = "article_id",nullable = false)
    private Integer articleId;
}
