package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private  long id;
    private  String title;
    private  String content;
    private Date createdAt;
    private Date updatedAt;

    @Builder
    public PostDto (long id,String title, String content,Date createdAt, Date updatedAt){
        this.id = id;
        this.title=title;
        this.content=content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
