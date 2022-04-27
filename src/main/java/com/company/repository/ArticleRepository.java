package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    Page<ArticleEntity> findByVisible(Boolean visible, Pageable pageable);

    Optional<ArticleEntity> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    Page<ArticleEntity>findByProfileId(Integer profileID, Pageable pageable);
    Page<ArticleEntity>findByRegionId(Integer profileID, Pageable pageable);
    Page<ArticleEntity>findByCategoryId(Integer profileID, Pageable pageable);
    Page<ArticleEntity>findByTypeId(Integer profileID, Pageable pageable);

    List<ArticleEntity>findTop4By(Sort sort);

    List<ArticleEntity>findTop4ByRegionId(Integer regionId, Sort sort);

    List<ArticleEntity>findTop4ByCategoryId(Integer regionId, Sort sort);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id = :id")
    int updateStatus(@Param("status")ArticleStatus status,@Param("id")Integer id);
}
