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
        return post.map(value -> new PostDto(value.getId(), value.getTitle(), value.getContent(),value.getCreatedAt(),value.getUpdatedAt())).orElse(null);
    }
    /*
    키워드가 포함 된 제목 전부 조회
    최신순
     조회 개수 최대 100개
     */
    public List<PostDto> getPostByTitle(String title) {
        List<Post> posts = springDataJpaPostRepository.findTop100ByTitleContainingOrderByCreatedAtDesc(title);
        List<PostDto> postDtos = new ArrayList<>();
        posts.forEach(
                post -> {
                    postDtos.add(new PostDto(post.getId(),post.getTitle(),post.getContent(),post.getCreatedAt(),post.getUpdatedAt()));
                }
        );
        return postDtos;
    }

    public PostDto addPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post savedPost = springDataJpaPostRepository.save(post);
        return new PostDto(savedPost.getId(),savedPost.getTitle(),savedPost.getContent(),savedPost.getCreatedAt(),savedPost.getUpdatedAt());
    }

    /*
    전체 조회
    최신순
    조회 개수 최대 100
    */
    public List<PostDto> getAllPosts(){
         List<Post> posts = springDataJpaPostRepository.findTop100ByOrderByCreatedAtDesc();
        List<PostDto> postDtos = new ArrayList<>();
       posts.forEach(
              post -> {
                  postDtos.add(new PostDto(post.getId(),post.getTitle(),post.getContent(),post.getCreatedAt(),post.getUpdatedAt()));
              }
       );
       return postDtos;
    }

    public PostDto updatePost(long id, PostDto postDto){
        Post post = springDataJpaPostRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Threre is no post id: "+id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedAt(post.getUpdatedAt());

        Post updatedPost = springDataJpaPostRepository.save(post); // 디비에 저장
        return new PostDto(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent(),updatedPost.getCreatedAt(),updatedPost.getUpdatedAt()); // dto 형태로 반환
    }

    public Optional<PostDto> deletePost(long id) {
        return springDataJpaPostRepository.findById(id)
                .map(op ->{
                    springDataJpaPostRepository.deleteById(id);
                    return new PostDto(op.getId(),op.getTitle(),op.getContent(),op.getCreatedAt(),op.getUpdatedAt());
                });
    }
}

