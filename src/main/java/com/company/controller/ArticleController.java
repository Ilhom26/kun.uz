package com.company.controller;

import com.company.dto.ArticleDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto, HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, pId));
    }

    @GetMapping("/public/list")
    public ResponseEntity<?> listByLang(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(articleService.list(page, size));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.update(id, dto, pId));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?>findByProfileId(@PathVariable("profileId") Integer profileId,
                                               @RequestParam(value = "page",defaultValue = "0") int page,
                                               @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(articleService.listByProfileId(profileId,page,size));
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?>findByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                            @RequestParam(value = "page",defaultValue = "0") int page,
                                            @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(articleService.listByCategoryId(categoryId,page,size));
    }
    @GetMapping("/type/{id}")
    public ResponseEntity<?>findByTypeId(@PathVariable("typeId") Integer typeId,
                                            @RequestParam(value = "page",defaultValue = "0") int page,
                                            @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(articleService.listByTypeId(typeId,page,size));
    }
    @GetMapping("/region/{id}")
    public ResponseEntity<?>findByRegionId(@PathVariable("regionId") Integer regionId,
                                            @RequestParam(value = "page",defaultValue = "0") int page,
                                            @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(articleService.listByRegionId(regionId,page,size));
    }

}
