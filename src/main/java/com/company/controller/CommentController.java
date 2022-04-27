package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.ProfileJwtDTO;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?>create(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(commentService.create(commentDTO,pId));
    }
    @PutMapping("/adm/{id}")
    public ResponseEntity<?>update(@PathVariable("id") Integer id,@RequestBody CommentDTO commentDTO,
                                   HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(commentService.update(id,commentDTO,pId));
    }
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?>delete(@PathVariable("id") Integer id,HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.getProfileFromHeader(request);
        return ResponseEntity.ok(commentService.delete(id, jwtDTO.getId(),jwtDTO.getRole()));
    }
    @GetMapping("/article/{id}")
    public ResponseEntity<?>findAllByArticleId(@PathVariable("id") Integer id,
                                    @RequestParam(value = "page",defaultValue = "0") int page,
                                    @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(commentService.listByArticleId(id,page,size));
    }
    @GetMapping("/adm/profile/{id}")
    public ResponseEntity<?>findAllByProfileId(@PathVariable("id") Integer id,
                                               @RequestParam(value = "page",defaultValue = "0") int page,
                                               @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(commentService.listByProfileId(id,page,size));
    }
    @GetMapping("/adm/list")
    public ResponseEntity<?>findAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                    @RequestParam(value = "size",defaultValue = "3") int size){
        return ResponseEntity.ok(commentService.list(page,size));
    }
}
