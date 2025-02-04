package com.recipe.backend.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private String token; // JWT 토큰 (JWT를 사용하는 경우)
}
