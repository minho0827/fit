package com.intellisyscorp.fitzme_android.models;

import lombok.Data;

@Data
public class UserCalendarResponseVO {
    private Integer user;
    private Integer extra;
    private String title;
    private String date;
}
