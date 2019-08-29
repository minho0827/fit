package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class UserOutfitVO {
    private Integer id;
    private OutfitVO outfit;
    private String nickname;
    private int frequency;
    private boolean is_like;
}
