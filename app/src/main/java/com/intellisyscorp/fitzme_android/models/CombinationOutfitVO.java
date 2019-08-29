package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class CombinationOutfitVO {
    private String next;
    private String previous;
    private String seed;
    public List<OutfitVO> results;
}

