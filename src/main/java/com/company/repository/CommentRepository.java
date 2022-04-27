package com.company.repository;

import com.company.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity,Integer> {
    Page<CommentEntity>findByArticleId(Integer articleID, Pageable pageable);
    Page<CommentEntity>findByProfileId(Integer profileID, Pageable pageable);
}
