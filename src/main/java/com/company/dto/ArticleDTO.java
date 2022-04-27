package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Boolean visible;
    private ArticleStatus status;

    private Integer viewCount;
    private Integer sharedCount;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime publishedDate;

    private Integer profileId;
    private Integer categoryId;
    private Integer typeId;
    private Integer regionId;
    private String attachId;

}
