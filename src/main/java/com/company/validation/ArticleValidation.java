package com.company.validation;

import com.company.dto.ArticleDTO;
import com.company.exp.AppBadRequestException;
import com.company.exp.AppForbiddenException;

public class ArticleValidation {
    public static void isValid(ArticleDTO dto) {
        if (dto.getTitle().trim().length() < 3 || dto.getTitle() == null) {
            throw new AppBadRequestException("Title Not Valid");
        }
        if (dto.getDescription().trim().length() < 3 || dto.getDescription() == null) {
            throw new AppBadRequestException("Description Not Valid");
        }
        if (dto.getContent().trim().length() < 3 || dto.getContent() == null) {
            throw new AppBadRequestException("Content Not Valid");
        }
        if(dto.getRegionId()==null){
            throw new AppForbiddenException("Region Not Valid");
        }
        if(dto.getCategoryId()==null){
            throw new AppForbiddenException("Category Not Valid");
        }
        if(dto.getTypeId()==null){
            throw new AppForbiddenException("Type Not Valid");
        }
        if(dto.getProfileId()==null){
            throw new AppForbiddenException("Profile Not Valid");
        }
        if(dto.getAttachId()==null){
            throw new AppForbiddenException("Attach Not Valid");
        }
    }
}
