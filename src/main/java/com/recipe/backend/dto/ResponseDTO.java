package com.recipe.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;
}
