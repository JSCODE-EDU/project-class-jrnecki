package com.example.demo.repository;

import com.example.demo.entity.Post;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findByTitle(String title);
}
