package com.example.demo.repository;


import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> { // 인터페이스가 인터페이스를 받을땐 extends

}
