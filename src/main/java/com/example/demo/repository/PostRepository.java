package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface PostRepository {
    Optional<Page<Post>> findAllByOrderByIdDesc(Pageable pageable);
   Optional< Page<Post>> findByTitleContaining( Pageable pageable,String keyword);
}
