package com.example.demo.service;
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
        Optional<Page<Post>> posts = springDataJpaPostRepository.findAllByOrderByIdDesc(pageable);
        if(posts.isPresent()){
           return posts.map(post -> post.map(PostDto :: toDto)).orElse(Page.empty());
        }
        return Page.empty();
    }
    
    public PostDto getPost(long id){
        Optional<Post> post = springDataJpaPostRepository.findById(id);
        if(post.isPresent()){
            return PostDto.toDto(post.get());
        }else{
            throw new RuntimeException("no post");
        }
    }

    public Page<PostDto> getPostByTitle(String keyword, Pageable pageable) {
        Optional<Page<Post>> posts = springDataJpaPostRepository.findByTitleContaining(pageable, keyword);
        if(posts.isPresent()){
            return posts.map(post -> post.map(PostDto :: toDto)).orElse(Page.empty());
        }
        return Page.empty();
    }

    public long addPost(PostDto postDto){
        Post post = Post.toEntity(postDto);
        springDataJpaPostRepository.save(post);
        return post.getId();
    }

    public long  updatePost(long id, PostDto postDto){
        Post post = springDataJpaPostRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Threre is no post id: "+id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedAt(post.getUpdatedAt());
        return id;
    }

    public String deletePost(long id) {
        Optional<Post> post = springDataJpaPostRepository.findById(id);
        if(post.isEmpty()){
            throw new IllegalStateException("없는 포스트..");
        }
        springDataJpaPostRepository.deleteById(id);
        return "has been deleted";
    }
}

