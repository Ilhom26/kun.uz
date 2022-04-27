package com.company.dto;

import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikeDTO {
    private Integer id;
    private LikeStatus like;
    private Integer profileId;
    private Integer articleId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
