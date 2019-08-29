package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class UserOutfitLikeResponseVO {
    private Integer id;
    private OutfitVO outfit;
    private String nickname;
    private Boolean is_like;
}
