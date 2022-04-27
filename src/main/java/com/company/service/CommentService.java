package com.company.service;

import com.company.dto.CommentDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.enums.ProfileRole;
import com.company.exp.AppForbiddenException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;

    public CommentDTO create(CommentDTO commentDTO, Integer pId){
        ArticleEntity articleEntity = articleService.get(commentDTO.getArticleId());
//validation
        CommentEntity commentEntity=new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setArticleId(commentDTO.getArticleId());
        commentEntity.setProfileId(pId);
        commentRepository.save(commentEntity);

        commentDTO.setId(commentEntity.getId());
        return commentDTO;
    }
    public boolean update(Integer id,CommentDTO commentDTO,Integer pId){
        CommentEntity commentEntity = get(id);
        if(!commentEntity.getProfileId().equals(pId)){
            throw new AppForbiddenException("Not access");
        }
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(commentEntity);
        return true;
    }
    public CommentEntity get(Integer id){
        return commentRepository.findById(id).orElseThrow(()->{
           throw new ItemNotFoundException("item not found");
        });
    }
    public boolean delete(Integer id, Integer pId, ProfileRole role){
        CommentEntity commentEntity = get(id);
        if(commentEntity.getProfileId().equals(pId) || role.equals(ProfileRole.ADMIN)){
            commentRepository.delete(commentEntity);
            return true;
        }
        throw new AppForbiddenException("Not access");
    }
    public PageImpl<CommentDTO>listByArticleId(Integer articleId,int page,int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,("createdDate")));
        Page<CommentEntity>pageList = commentRepository.findByArticleId(articleId,pageable);

        List<CommentDTO>commentDTOList = new LinkedList<>();
        for(CommentEntity commentEntity:pageList.getContent()){
            commentDTOList.add(toDTO(commentEntity));
        }
        return new PageImpl<CommentDTO>(commentDTOList,pageable,pageList.getTotalElements());
    }
    public PageImpl<CommentDTO>listByProfileId(Integer profileId,int page,int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,("createdDate")));
        Page<CommentEntity>pageList = commentRepository.findByProfileId(profileId,pageable);

        List<CommentDTO>commentDTOList = new LinkedList<>();
        for(CommentEntity commentEntity:pageList.getContent()){
            commentDTOList.add(toDTO(commentEntity));
        }
        return new PageImpl<CommentDTO>(commentDTOList,pageable,pageList.getTotalElements());
    }
    public PageImpl<CommentDTO>list(int page,int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,("createdDate")));
        Page<CommentEntity>pageList = commentRepository.findAll(pageable);

        List<CommentDTO>commentDTOList = new LinkedList<>();
        for(CommentEntity commentEntity:pageList.getContent()){
            commentDTOList.add(toDTO(commentEntity));
        }
        return new PageImpl<CommentDTO>(commentDTOList,pageable,pageList.getTotalElements());
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

}
