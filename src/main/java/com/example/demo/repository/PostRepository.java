package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository {
    List<Post> findTop100ByOrderByCreatedAtDesc();
    List<Post>findTop100ByTitleContainingOrderByCreatedAtDesc(String keyword);
}
