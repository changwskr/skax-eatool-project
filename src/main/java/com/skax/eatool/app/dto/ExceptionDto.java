package com.skax.eatool.app.dto;

import lombok.*;
import com.skax.eatool.app.exception.CustomException;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private Integer code;
    private String message;

    public ExceptionDto(CustomException e) {
        this.code = e.getErrorCode().getCode();
        this.message = e.getMessage();
    }

    public ExceptionDto(Exception e) {
        this.code = e.getClass().getSimpleName().hashCode();
        this.message = e.getMessage();
    }
}
