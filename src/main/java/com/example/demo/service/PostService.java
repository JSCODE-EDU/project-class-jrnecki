package com.example.demo.service;
import com.example.demo.Exception.DomainErrorCode;
import com.example.demo.dto.PostDto;
import com.example.demo.entity.Post;
import com.example.demo.repository.SpringDataJpaPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
/*
   요구사항 의도에 더 적합하게
   pageable 이용하는 것으로 변경

   2023.06.25
   */
@Service
@Transactional
public class PostService {
    private final SpringDataJpaPostRepository springDataJpaPostRepository;

    public PostService(SpringDataJpaPostRepository springDataJpaPostRepository) {
        this.springDataJpaPostRepository = springDataJpaPostRepository;
    }

    public Page<PostDto> getAllPosts(Pageable pageable){
        var posts=springDataJpaPostRepository.findAllByOrderByIdDesc(pageable);
        return   posts.map(post -> post.map(PostDto :: toDto))
                .orElseThrow(DomainErrorCode.NO_POST::create);
    }
    
    public PostDto getPost(long id){
        Optional<Post> post = springDataJpaPostRepository.findById(id);
        return post.map(PostDto::toDto) .orElseThrow(DomainErrorCode.NO_POST::create);
    }

    public Page<PostDto> getPostByTitle(String keyword, Pageable pageable) {
        var posts = springDataJpaPostRepository.findByTitleContaining(pageable,keyword);

        return posts.map(post -> post.map(PostDto :: toDto))
                .orElseThrow(DomainErrorCode.NO_POST::create);
    }

    public long addPost(PostDto postDto){
        var newPost = Post.toEntity(postDto);
        springDataJpaPostRepository.save(newPost);

        return newPost.getId();
    }

    public long  updatePost(long id, PostDto postDto){
        var beforePost = springDataJpaPostRepository.findById(id)
                .orElseThrow(DomainErrorCode.POST_ID_ERROR::create);
        beforePost.update(postDto.getTitle(), postDto.getContent(), postDto.getUpdatedAt());

        return id;
    }

    public void  deletePost(long id) {
        var post = springDataJpaPostRepository.findById(id)
                .orElseThrow(DomainErrorCode.POST_ID_ERROR::create);
        springDataJpaPostRepository.deleteById(id);
    }
}

