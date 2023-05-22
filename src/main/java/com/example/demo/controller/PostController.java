package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    public @Valid ResponseEntity<List<PostDto>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
}
    @PostMapping("/new")
    public ResponseEntity <?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
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
    @PutMapping("/edit/{id}")
    public ResponseEntity<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body( postService.updatePost(id,postDto));
    }
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

