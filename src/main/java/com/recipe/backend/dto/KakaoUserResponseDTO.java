package com.recipe.backend.dto;


import com.recipe.backend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserResponseDTO {
    private String kakaoId;
    private String nickName;
    private String email;
    private String profileImageUrl;

    public static KakaoUserResponseDTO fromEntity(UserEntity user) {
        return new KakaoUserResponseDTO(
                user.getKakaoId(),
                user.getNickName(),
                user.getEmail(),
                user.getProfileImageUrl()
        );
    }
}
