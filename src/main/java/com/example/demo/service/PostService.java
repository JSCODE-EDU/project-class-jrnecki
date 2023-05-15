package com.example.demo.service;
import com.example.demo.dto.PostDto;
import com.example.demo.entity.Post;
import com.example.demo.repository.SpringDataJpaPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final SpringDataJpaPostRepository springDataJpaPostRepository;

    public PostService(SpringDataJpaPostRepository springDataJpaPostRepository) {
        this.springDataJpaPostRepository = springDataJpaPostRepository;
    }

    public PostDto getPost(long id){
        Optional<Post> post = springDataJpaPostRepository.findById(id);
        return post.map(value -> new PostDto(value.getId(), value.getTitle(), value.getContent())).orElse(null);
    }
    public PostDto getPostByTitle(String title){
        Optional<Post> post = springDataJpaPostRepository.findByTitle(title);
        return post.map(value -> new PostDto(value.getId(), value.getTitle(), value.getContent())).orElse(null);
    }

    public PostDto addPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post savedPost = springDataJpaPostRepository.save(post);
        return new PostDto(savedPost.getId(),savedPost.getTitle(),savedPost.getContent());
    }

    public List<PostDto> getAllPosts(){
        List<Post> posts = springDataJpaPostRepository.findAll();
       List<PostDto> postDtos = new ArrayList<>();
       posts.forEach(
               post -> postDtos.add(new PostDto(post.getId(),post.getTitle(),post.getContent()))
       );
       return postDtos;
    }

    public PostDto updatePost(long id, PostDto postDto){
        Post post = springDataJpaPostRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Threre is no post id: "+id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = springDataJpaPostRepository.save(post); // 디비에 저장
        return new PostDto(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent()); // dto 형태로 반환
    }

    public void deletePost(long id) {
        springDataJpaPostRepository.deleteById( id);
    }
}

