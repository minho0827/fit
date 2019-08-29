package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class OutfitVO {
    private String id;
    private GarmentVO top;
    private GarmentVO bottom;
    private GarmentVO outer;
    private GarmentVO dress;
    private GarmentVO shoes;
    private boolean is_like;
}
