package com.intellisyscorp.fitzme_android.models;

import lombok.Data;


@Data
public class UserLikeOutfitVO {
    private int id;
    private OutfitVO outfit;
    private String nickname;
}
