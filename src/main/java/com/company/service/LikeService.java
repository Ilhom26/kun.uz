package com.company.service;

import com.company.dto.LikeDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.enums.ProfileRole;
import com.company.exp.AppForbiddenException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ArticleService articleService;

    public LikeDTO create(LikeDTO likeDTO,Integer pId){
//        ArticleEntity articleEntity = articleService.get(likeDTO.getArticleId());
//validation
        Optional<LikeEntity>optional=likeRepository.findByArticleIdAndProfileId(likeDTO.getArticleId(), likeDTO.getProfileId());
        if(optional.isPresent()){
            optional.get().setStatus(likeDTO.getLike());
            likeRepository.save(optional.get());
            return likeDTO;
        }

        LikeEntity likeEntity=new LikeEntity();
        likeEntity.setStatus(likeDTO.getLike());
        likeEntity.setArticleId(likeDTO.getArticleId());
        likeEntity.setProfileId(pId);
        likeRepository.save(likeEntity);
        likeDTO.setId(likeEntity.getId());
        return likeDTO;
    }
    public boolean update(Integer id,LikeDTO likeDTO,Integer pId){
        LikeEntity likeEntity=get(id);
        if(!likeEntity.getProfileId().equals(pId)){
            throw new AppForbiddenException("Not access");
        }

        likeEntity.setStatus(likeDTO.getLike());
        likeEntity.setUpdatedDate(LocalDateTime.now());
        likeRepository.save(likeEntity);
        return true;
    }
    public boolean delete(Integer id, Integer pId, ProfileRole role){
        LikeEntity likeEntity=get(id);
        if(likeEntity.getProfileId().equals(pId) || role.equals(ProfileRole.ADMIN)){
            likeRepository.delete(likeEntity);
            return true;
        }
        throw new AppForbiddenException("Not access");
    }
    public PageImpl<LikeDTO>listByArticleID(Integer articleId,int page,int size){
        Pageable pageable= PageRequest.of(page, size, Sort.Direction.DESC,"createdDate");
        Page<LikeEntity>pageList = likeRepository.findByArticleId(articleId,pageable);

        List<LikeDTO>dtoList=new LinkedList<>();
        for(LikeEntity likeEntity:pageList.getContent()){
            dtoList.add(toDTO(likeEntity));
        }
        return new PageImpl<LikeDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<LikeDTO>listBYProfileId(Integer profileId,int page,int size){
        Pageable pageable = PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<LikeEntity>pageList = likeRepository.findByProfileId(profileId,pageable);

        List<LikeDTO>dtoList=new LinkedList<>();
        for(LikeEntity likeEntity:pageList.getContent()){
            dtoList.add(toDTO(likeEntity));
        }
        return new PageImpl<LikeDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<LikeDTO>list(int page,int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<LikeEntity>pageList = likeRepository.findAll(pageable);

        List<LikeDTO>dtoList=new LinkedList<>();
        for (LikeEntity likeEntity:pageList.getContent()){
            dtoList.add(toDTO(likeEntity));
        }
        return new PageImpl<LikeDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public LikeDTO checkLike(Integer articleId,Integer pId){
        Optional<LikeEntity>optional=likeRepository.findByArticleIdAndProfileId(articleId,pId);
        if(optional.isPresent()){
            return toDTO(optional.get());
        }
        return new LikeDTO();
    }

    public LikeEntity get(Integer id){
        return likeRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });
    }
    public LikeDTO toDTO(LikeEntity entity){
        LikeDTO dto = new LikeDTO();
        dto.setId(entity.getId());
        dto.setArticleId(entity.getArticleId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

}
