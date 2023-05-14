package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PostDto {
    private  long id;
    private  String title;
    private  String content;
    @Builder
    public PostDto (long id,String title, String content){
        this.id = id;
        this.title=title;
        this.content=content;
    }
}
