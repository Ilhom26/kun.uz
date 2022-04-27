package com.company.service;

import com.company.dto.ArticleDTO;
import com.company.dto.LikeDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.exp.AppForbiddenException;
import com.company.exp.ItemAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import com.company.validation.ArticleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;

    public ArticleDTO create(ArticleDTO dto, Integer pId) {
        // ProfileEntity profileEntity = profileService.get(pId);
        ArticleValidation.isValid(dto); // validation

        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if (optional.isPresent()) {
            throw new ItemAlreadyExistsException("This Article already used!");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setProfileId(pId);

        entity.setStatus(ArticleStatus.CREATED);

        entity.setAttachId(dto.getAttachId());
        entity.setTypeId(dto.getTypeId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        // entity.setProfile(profileEntity);
        articleRepository.save(entity);
        return toDTO(entity);
    }

    public List<ArticleDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ArticleDTO> dtoList = new ArrayList<>();

        articleRepository.findByVisible(true, pageable).forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public ArticleDTO update(Integer id, ArticleDTO dto, Integer pId) {
        ProfileEntity profileEntity = profileService.get(pId);

        ArticleValidation.isValid(dto); // validation

        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        ;
        entity.setProfile(profileEntity);
        entity.setUpdatedDate(LocalDateTime.now());

        articleRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        ArticleEntity entity = articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not Found!"));

        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        int n = articleRepository.updateVisible(false, id);
        return n > 0;
    }
    public PageImpl<ArticleDTO>listByProfileId(Integer profileId,int page,int size){
        Pageable pageable=PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<ArticleEntity>pageList=articleRepository.findByProfileId(profileId,pageable);

        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:pageList.getContent()){
            dtoList.add(toDTO(articleEntity));
        }
        return new PageImpl<ArticleDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<ArticleDTO>listByRegionId(Integer regionId,int page,int size){
        Pageable pageable=PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<ArticleEntity>pageList=articleRepository.findByRegionId(regionId,pageable);

        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:pageList.getContent()){
            dtoList.add(toDTO(articleEntity));
        }
        return new PageImpl<ArticleDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<ArticleDTO>listByCategoryId(Integer categoryId,int page,int size){
        Pageable pageable=PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<ArticleEntity>pageList=articleRepository.findByCategoryId(categoryId,pageable);

        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:pageList.getContent()){
            dtoList.add(toDTO(articleEntity));
        }
        return new PageImpl<ArticleDTO>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<ArticleDTO>listByTypeId(Integer typeId,int page,int size){
        Pageable pageable=PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<ArticleEntity>pageList=articleRepository.findByTypeId(typeId,pageable);

        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:pageList.getContent()){
            dtoList.add(toDTO(articleEntity));
        }
        return new PageImpl<ArticleDTO>(dtoList,pageable,pageList.getTotalElements());
    }
   /* public PageImpl<ArticleDTO>list(int page,int size){
        Pageable pageable=PageRequest.of(page, size,Sort.Direction.DESC,"createdDate");
        Page<ArticleEntity>pageList=articleRepository.findAll(pageable);

        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:pageList.getContent()){
            dtoList.add(toDTO(articleEntity));
        }
        return new PageImpl<ArticleDTO>(dtoList,pageable,pageList.getTotalElements());
    }*/
    public List<ArticleDTO>list4(){
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        List<ArticleEntity>entityList=articleRepository.findTop4By(sort);
        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:entityList){
            dtoList.add(toDTO(articleEntity));
        }
        return dtoList;
    }
    public List<ArticleDTO>listRegionId4(Integer regionID){
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        List<ArticleEntity>entityList=articleRepository.findTop4ByRegionId(regionID,sort);
        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:entityList){
            dtoList.add(toDTO(articleEntity));
        }
        return dtoList;
    }
    public List<ArticleDTO>listCategory4(Integer categoryId){
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        List<ArticleEntity>entityList=articleRepository.findTop4ByCategoryId(categoryId,sort);
        List<ArticleDTO>dtoList=new LinkedList<>();
        for (ArticleEntity articleEntity:entityList){
            dtoList.add(toDTO(articleEntity));
        }
        return dtoList;
    }
    public boolean publish(Integer id){
       Optional<ArticleEntity>optional = articleRepository.findById(id);
       if(optional.isEmpty()){
           throw new ItemNotFoundException("Item not found");
       }
       if(!optional.get().getStatus().equals(ArticleStatus.CREATED)){
           throw new ItemNotFoundException("Item not found");
       }
       articleRepository.updateStatus(ArticleStatus.CREATED,id);
       return true;
    }
    public boolean block(Integer id){
        Optional<ArticleEntity>optional = articleRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Item not found");
        }
        if(!optional.get().getStatus().equals(ArticleStatus.CREATED)){
            throw new ItemNotFoundException("Item not found");
        }
        articleRepository.updateStatus(ArticleStatus.BLOCKED,id);

        return true;
    }


    public ArticleEntity get (Integer id){
      return articleRepository.findById(id).orElseThrow(()->{
        throw new ItemNotFoundException("Item not foudn");
      });
    }
    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setProfileId(entity.getProfileId());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }


}
