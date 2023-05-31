package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
error 내용을 담을 entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseEntity {
    private int status;
    private int code;
    private String message;

}
