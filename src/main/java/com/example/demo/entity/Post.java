package com.example.demo.entity;

import com.example.demo.dto.PostDto;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @Builder
    public static Post toEntity (PostDto postDto){
        return Post.builder()
                .id(postDto.getPostId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }
    public void update(String title, String content, Date updatedAt){
        this.title = title;
        this.content=content;
        super.setUpdatedAt( updatedAt);
    }
}
