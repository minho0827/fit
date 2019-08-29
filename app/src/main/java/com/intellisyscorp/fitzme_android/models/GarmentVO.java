package com.intellisyscorp.fitzme_android.models;

import java.util.List;

import lombok.Data;

@Data
public class GarmentVO {
    private int id;
    private String part;
    private String category;
    private String url;
    private String originalColor;
    private String image;
    private List<String> weathers;
    private List<String> tags;
    private List<String> colors;
}
