package com.company.controller;

import com.company.dto.LikeDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.entity.LikeEntity;
import com.company.service.LikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/adm")
    public ResponseEntity<?>create(@RequestBody LikeDTO likeDTO, HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(likeService.create(likeDTO,pId));
    }
    @PutMapping("/adm/{id}")
    public ResponseEntity<?>update(@PathVariable("id") Integer id,@RequestBody LikeDTO likeDTO,
                                   HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(likeService.update(id,likeDTO,pId));
    }
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?>delete(@PathVariable("id") Integer id,HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfileFromHeader(request);
        return ResponseEntity.ok(likeService.delete(id, jwtDTO.getId(),jwtDTO.getRole()));
    }
    @GetMapping("/article/{id}")
    public ResponseEntity<?>findAllByArticleId(@PathVariable("id") Integer id,
                                               @RequestParam(value = "page",defaultValue = "0") int page,
                                               @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(likeService.listByArticleID(id,page,size));
    }
    @GetMapping("/adm/profile/{id}")
    public ResponseEntity<?>findAllByProfileId(@PathVariable("id") Integer id,
                                               @RequestParam(value = "page",defaultValue = "0") int page,
                                               @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(likeService.listBYProfileId(id,page,size));
    }
    @GetMapping("/adm/list")
    public ResponseEntity<?>findAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                    @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(likeService.list(page,size));
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?>getArticleId(@PathVariable("id") Integer id,HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(likeService.checkLike(id,pId));
    }
}
