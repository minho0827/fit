package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class UserOutfitPagingVO {
    private String next;
    private String previous;
    public List<UserOutfitVO> results;
}
