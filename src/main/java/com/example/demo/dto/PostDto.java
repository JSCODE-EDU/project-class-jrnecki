package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
public class PostDto {
    private  long id;

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
    public PostDto (long id,String title, String content,Date createdAt, Date updatedAt){
        this.id = id;
        this.title=title;
        this.content=content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
