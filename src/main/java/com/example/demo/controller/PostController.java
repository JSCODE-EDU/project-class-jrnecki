package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Tag(name="post", description="post api")
@RestController
@RequiredArgsConstructor // 생성자 자동 주입
@RequestMapping(value="/post")
public class PostController {

    private final PostService postService;
    @GetMapping()
    public String gohome(){
        return  "index.html";
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id){
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("/title")
    public ResponseEntity<Page<PostDto>> getPostByKeyword(
            @PageableDefault(size=10,sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable,
            @Nullable @RequestParam String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByTitle(keyword,pageable));
    }

    @GetMapping("/all")
    public  ResponseEntity<Page<PostDto>>  getAllPost
            (@PageableDefault (size = 10, sort="createdAt",direction = Sort.Direction.DESC)
             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts(pageable));
}

    @PostMapping("/new")
    public ResponseEntity <?> createPost(
            @Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
             List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error ->{
                 errors.add(error.getDefaultMessage());
             });
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postService.addPost(postDto));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Long> editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body( postService.updatePost(id,postDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long  id){
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

