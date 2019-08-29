package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class GarmentPagingVO {
    private String next;
    private String previous;
    public List<GarmentVO> results;
}
