package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DomainErrorCode {
    POST(1000,""),
    POST_ID_ERROR(POST.code+1, "해당 포스트는 존재하지 않습니다."),
    NO_POST(POST.code+2, "포스트가 없습니다.")
    ;
    private final Integer code;
    private final String message;

    public DomainException create(){
        return new DomainException(this.code, this.message);
    }
}
