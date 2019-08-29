package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class WeatherVO {
    private Integer seq;
    private String date;
    private String temp;
    private String temp_min;
    private String temp_max;
    private String sky;
    private String pty;
    private String weather;
    private String rain_probability;
    private String humidity;
}
