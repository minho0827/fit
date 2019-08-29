package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class UserGarmentVO {
    private Integer id;
    private GarmentVO garment;
    private String nickname;
    private Integer frequency;
    private boolean is_hold;
}
