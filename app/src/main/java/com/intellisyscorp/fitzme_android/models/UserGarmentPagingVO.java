package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class UserGarmentPagingVO {
    private String next;
    private String previous;
    public List<UserGarmentVO> results;
}
