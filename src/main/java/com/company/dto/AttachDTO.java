package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDTO {
    private String id;
    private String url;
    private String origenName;
    private LocalDateTime createdDate;
    private String path;
}
