package com.example.demo.repository;

import com.example.demo.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(long id);
    Optional<Post> findByTitle(String title);
    List<Post> findAll();
    Post updatePost(Post post);
}
