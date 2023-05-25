package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;


@Tag(name="post", description="post api")
@RestController
@RequiredArgsConstructor // 생성자 자동 주입
@RequestMapping(value="/post")

public class PostController {

    private final PostService postService;
    @GetMapping("/{id}")
    @Operation(summary = "search post by id",description="inquire post by unique id")
    public ResponseEntity<PostDto> getPost(@PathVariable long id){

        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("/title")
    @Operation(summary = "search post by keyword",description="inquire posts which title contains keyword.")
    public ResponseEntity<List<PostDto>> getPostByKeyword(
            @PageableDefault(size=10,sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable,
            @Nullable @RequestParam String keyword){
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPostByTitle(keyword,pageable));
    }

    @Operation(summary = "get all posts",description="inquire latest 100 posts ")
    @GetMapping("/all")
    public  ResponseEntity<List<PostDto>> getAllPost
            (@PageableDefault (size = 10, sort="createdAt",direction = Sort.Direction.DESC)
             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts(pageable));
}

    @Operation(summary = "write a post",description="write a post ")
    @PostMapping("/new")
    public ResponseEntity <?> createPost(
            @Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
             List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error ->{
                 errors.add(error.getDefaultMessage());
             });
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(postService.addPost(postDto));
        }
    }

    @Operation(summary = "edit a  post",description="edit a  post with a specific id")
    @PutMapping("/edit/{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body( postService.updatePost(id,postDto));
    }

    @Operation(summary = "delete a  post",description="delete a  post with a specific id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long  id){
        Optional<PostDto> optionalPostDto = postService.deletePost(id);
        if(optionalPostDto.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Has been deleted.");
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Check id. This post does not exist.");
        }
        }
    }

