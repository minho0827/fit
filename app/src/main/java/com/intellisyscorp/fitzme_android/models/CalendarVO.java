package com.intellisyscorp.fitzme_android.models;

import lombok.Data;


@Data
public class CalendarVO {
    private int id;
    private String title;
    private String date;
    private ExtraData obj;

    @Data
    public class ExtraData {
        private int id;
        private GarmentVO garment;
        private OutfitVO outfit;
        private String image;
    }
}
