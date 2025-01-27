package com.recipe.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // kakaoId는 고유값
    private String kakaoId;

    private String nickName;

    private String email;

    private String profileImageUrl;

    public UserEntity(String kakaoId, String nickName, String email, String profileImageUrl) {
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    // 닉네임 업데이트
    public UserEntity updateNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    // 이메일 업데이트
    public UserEntity updateEmail(String email) {
        this.email = email;
        return this;
    }

    // 프로필 이미지 업데이트
    public UserEntity updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        return this;
    }
}
