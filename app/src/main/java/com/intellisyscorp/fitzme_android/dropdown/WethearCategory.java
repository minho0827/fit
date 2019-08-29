package com.intellisyscorp.fitzme_android.dropdown;


import com.intellisyscorp.fitzme_android.R;

import java.util.ArrayList;
import java.util.List;

public class WethearCategory {
    public long id;
    public int iconRes;
    public String category;

    public WethearCategory(long id, int iconRes, String category) {
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List<WethearCategory> generateCategoryList() {
        List<WethearCategory> categories = new ArrayList<>();
        String[] programming = {"SPRING", "SUMMER", "FALL", "WINTER"};

        for (int i = 0; i < programming.length; i++) {
            categories.add(new WethearCategory(i, R.drawable.sun, programming[i]));
        }
        return categories;
    }

}
