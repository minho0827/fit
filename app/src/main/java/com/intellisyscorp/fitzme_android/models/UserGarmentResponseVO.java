package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class UserGarmentResponseVO {
    private Integer user;
    private Integer garment;
    private String nickname;
    private List<String> non_field_errors;
}
