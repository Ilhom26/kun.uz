package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer id;
    private Integer profileId;
    private Integer articleId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
