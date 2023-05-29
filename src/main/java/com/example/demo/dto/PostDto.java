package com.example.demo.dto;

import com.example.demo.entity.Post;
import lombok.Builder;
import lombok.Data;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@Builder
public class PostDto {
    private  long  postId;

    @NotBlank(message = "The title cannot be consist of only blanks.")
    @Size(min=1,max=15,message = "The title must be at least 1 and no more than 15 characters.")
    @Column(length=15)
    private  String title;

    @NotEmpty(message="The title cannot be empty")
    @Size(min=1,max=1000,message = "The title must be at least 1 and no more than 1000 characters.")
    @Column(length=1000)
    private  String content;

    private Date createdAt;
    private Date updatedAt;

    @Builder
    public static PostDto toDto(Post post){
        return PostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

}
}
