package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor /* 생성자 자동 주입*/
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id){

        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("/title")
    public ResponseEntity<List<PostDto>> getPostByKeyword(@RequestParam("keyword") String keyword){
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPostByTitle(keyword));
    }
@GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
}
    @PostMapping("/new")
    public ResponseEntity <PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body(postService.addPost(postDto));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body( postService.updatePost(id,postDto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long  id){
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제되었습니다.");
        }
    }

